package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.model.Data
import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shared.model.LaunchesLoadSpec
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeLaunchRepository : LaunchRepository {

    var data: Map<LaunchesLoadSpec, Data<List<Launch>>> = emptyMap()

    override fun observeLaunches(spec: LaunchesLoadSpec): Flow<Data<List<Launch>>> =
        flowOf(data[spec]!!)
}