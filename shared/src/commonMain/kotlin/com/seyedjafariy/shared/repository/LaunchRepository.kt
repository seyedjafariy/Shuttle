package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.components.main.SpaceXAPI
import com.seyedjafariy.shared.model.Data
import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.LaunchesLoadSpec
import com.seyedjafariy.shared.model.dto.QueryRequestDTO
import com.seyedjafariy.shared.network.successBody
import com.seyedjafariy.shared.utils.Log
import kotlinx.coroutines.flow.*

interface LaunchRepository {
    fun observeLaunches(spec: LaunchesLoadSpec): Flow<Data<List<Launch>>>
}

class LaunchRepositoryImpl(
    private val api: SpaceXAPI,
    private val localDataSource: LaunchLocalDataSource,
    private val queryFactory: LaunchQueryDTOFactory,
    private val keyValueStore: KeyValueStore,
) : LaunchRepository {

    override fun observeLaunches(spec: LaunchesLoadSpec): Flow<Data<List<Launch>>> = flow {
        val cachedData = if (keyValueStore.contains(spec.toString())) {
            val ids = getCachedLaunchIds(spec)
            val cachedList = localDataSource.observeAllWithIds(ids).first()

            cachedList
        } else {
            emptyList()

        }
        emit(Data(cachedData, true))

        val updateSuccessful = updateLaunches(spec)

        if (!updateSuccessful) {
            emit(Data(cachedData, false))
            return@flow
        }

        val cachedIds = getCachedLaunchIds(spec)
        emitAll(
            localDataSource
                .observeAllWithIds(cachedIds)
                .map { Data(it, false) }
        )
    }

    private fun getCachedLaunchIds(spec: LaunchesLoadSpec): List<String> = keyValueStore.get(spec.toString()).split(SEPARATOR_LAUNCH_ID)

    private suspend fun updateLaunches(spec: LaunchesLoadSpec): Boolean {
        val launchesResponse = fetchLaunches(queryFactory.createQueryDTO(spec))
        if (launchesResponse.isNotSuccessful) {
            Log.e(launchesResponse.error.throwable) { "fetching launches failed" }
            return false
        }

        val launches = launchesResponse.successBody.docs

        keyValueStore.put(spec.toString(), launches.map { it.id }.joinToString(SEPARATOR_LAUNCH_ID))

        localDataSource.insertAll(launches)

        return true
    }


    private suspend fun fetchLaunches(queryDto: QueryRequestDTO) = api.getLaunches(queryDto)
}

private const val SEPARATOR_LAUNCH_ID = ","

