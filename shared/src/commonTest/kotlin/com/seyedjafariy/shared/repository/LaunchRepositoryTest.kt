package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.components.main.FakeSpaceXAPI
import com.seyedjafariy.shared.model.LaunchesLoadSpec
import com.seyedjafariy.shared.model.createSampleLaunch
import com.seyedjafariy.shared.model.createSampleLaunchDTO
import com.seyedjafariy.shared.model.dto.QueryResponseDTO
import com.seyedjafariy.shared.model.mappers.toDomain
import com.seyedjafariy.shared.network.Response
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LaunchRepositoryTest {

    private val api = FakeSpaceXAPI()
    private val datasource = FakeLaunchLocalDataSource()
    private val valueStore = MemoryKeyValueStore()
    private val queryFactory = LaunchQueryDTOFactory()
    private val repository = LaunchRepositoryImpl(api, datasource, queryFactory, valueStore)

    @Test
    fun `GIVE cache is empty WHEN observed THEN data is loaded from API and saved and returned`() = runTest {
        val sampleDTO = createSampleLaunchDTO()
        val queryDto = QueryResponseDTO(listOf(sampleDTO))
        val loadSpec = LaunchesLoadSpec()

        api.launchResponse = mapOf(
            queryFactory.createQueryDTO(loadSpec) to Response.Success.WithBody(200, queryDto)
        )

        val values = repository.observeLaunches(loadSpec).toList()

        assertEquals(listOf(sampleDTO.toDomain()), datasource.cache[listOf(sampleDTO.id)])
        assertEquals(sampleDTO.id, valueStore.cache[loadSpec.toString()])

        assertEquals(2, values.size)
        assertEquals(sampleDTO.id, values[1].content.first().id)
    }

    @Test
    fun `GIVE cache is NOT empty WHEN observed THEN first the cached data is returned`() = runTest {
        val sampleDTO = createSampleLaunchDTO()
        val queryDto = QueryResponseDTO(listOf(sampleDTO))
        val loadSpec = LaunchesLoadSpec()

        val cachedId = "cached"
        val cachedLaunch = createSampleLaunch().copy(id = cachedId)
        valueStore.cache[loadSpec.toString()] = cachedId
        datasource.cache[listOf(cachedId)] = listOf(cachedLaunch)

        api.launchResponse = mapOf(
            queryFactory.createQueryDTO(loadSpec) to Response.Success.WithBody(200, queryDto)
        )

        val values = repository.observeLaunches(loadSpec).toList()

        assertEquals(cachedId, values[0].content.first().id)
    }
}