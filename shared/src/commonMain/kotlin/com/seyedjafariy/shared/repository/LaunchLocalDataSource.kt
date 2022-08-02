package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shared.model.mappers.toDomain
import com.seyedjafariy.shuttle.database.Launches
import com.seyedjafariy.shuttle.database.LaunchesQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LaunchLocalDataSource {

    fun observeAllWithIds(ids: List<String>): Flow<List<Launch>>
    fun insertAll(launches: List<LaunchDTO>)
}

class LaunchLocalDataSourceImpl(
    private val queries: LaunchesQueries,
) : LaunchLocalDataSource {
    override fun observeAllWithIds(ids: List<String>): Flow<List<Launch>> {
        val indexedIds: Map<String, Int> = ids.withIndex().associate { it.value to it.index }

        return queries.getAllWithIds(ids)
            .asFlow()
            .mapToList()
            .map {
                it.sortByIds(indexedIds).map { it.toDomain() }
            }
    }

    private fun List<Launches>.sortByIds(ids: Map<String, Int>): List<Launches> =
        sortedBy { ids[it.id]!! }

    override fun insertAll(launches: List<LaunchDTO>) {
        queries.transaction {
            launches.forEach { launch ->
                launch.insertLaunch()
            }
        }
    }

    private fun LaunchDTO.insertLaunch() {
        queries.insert(
            id = id,
            name = name,
            youtube = links.webcast,
            article = links.article,
            wikipedia = links.wikipedia,
            small_image = links.patch.small,
            large_image = links.patch.large,
            rocket_id = rocket.id,
            rocket_name = rocket.name,
            rocket_type = rocket.type,
            successful = Launch.SuccessState(success).rawName,
            date = dateUtc,
            upcoming = upcoming,
        )
    }

}