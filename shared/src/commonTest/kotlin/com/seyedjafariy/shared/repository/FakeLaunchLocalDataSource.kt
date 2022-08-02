package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shared.model.mappers.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLaunchLocalDataSource : LaunchLocalDataSource {

    val cache = mutableMapOf<List<String>, List<Launch>>()

    override fun observeAllWithIds(ids: List<String>): Flow<List<Launch>> =
        flowOf(cache[ids]!!)

    override fun insertAll(launches: List<LaunchDTO>) {
        cache[launches.map { it.id }] = launches.map { it.toDomain() }
    }

}