package com.seyedjafariy.shared.components.main

import com.arkivanov.mvikotlin.core.utils.isAssertOnMainThreadEnabled
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.seyedjafariy.shared.model.*
import com.seyedjafariy.shared.repository.FakeCompanyInfoRepository
import com.seyedjafariy.shared.repository.FakeLaunchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainStoreTest {

    private val launchRepository = FakeLaunchRepository()
    private val companyInfoRepository = FakeCompanyInfoRepository()

    private val mainStore = MainStoreProvider(DefaultStoreFactory(), launchRepository, companyInfoRepository)

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        isAssertOnMainThreadEnabled = false
    }

    @AfterTest
    fun tearDown() {
        isAssertOnMainThreadEnabled = true
    }

    @Test
    fun `GIVEN store is provided WHEN subscribed THEN initial companyInfo and Launches are loaded`() {
        launchRepository.data = mapOf(LaunchesLoadSpec() to Data(emptyList(), true))

        val store = mainStore.provide()

        val state: MainComponent.State = store.state

        assertEquals(
            MainComponent.State(
                Data(null, true),
                Data(emptyList(), true)
            ), state
        )
    }

    @Test
    fun `GIVEN store is provided WHEN subscribed THEN companyInfo and Launches are loaded`() {
        val sampleLaunch = createSampleLaunch()
        val companyInfo = createCompanyInfo()

        val launchData = Data(listOf(sampleLaunch), true)
        val companyInfoData: Data<CompanyInfo?> = Data(companyInfo, true)

        launchRepository.data = mapOf(LaunchesLoadSpec() to launchData)
        companyInfoRepository.data = listOf(companyInfoData)

        val store = mainStore.provide()

        val state: MainComponent.State = store.state

        assertEquals(
            MainComponent.State(
                companyInfoData,
                launchData
            ), state
        )
    }

    @Test
    fun `GIVEN store is loaded WHEN update is called with new spec THEN launch data is refreshed from repo`() {
        val sampleLaunch = createSampleLaunch()
        val companyInfo = createCompanyInfo()

        val launchData = Data(listOf(sampleLaunch), true)
        val companyInfoData: Data<CompanyInfo?> = Data(companyInfo, true)

        val updatedSpec = LaunchesLoadSpec(operationStatus = LaunchesLoadSpec.OperationStatus.Success)

        launchRepository.data = mapOf(
            LaunchesLoadSpec() to Data(emptyList(), true),
            updatedSpec to launchData
        )
        companyInfoRepository.data = listOf(companyInfoData)

        val store = mainStore.provide()

        store.accept(MainStore.Intent.Update(updatedSpec))

        val state: MainComponent.State = store.state

        assertEquals(
            MainComponent.State(
                companyInfoData,
                launchData,
                updatedSpec,
            ), state
        )
    }
}