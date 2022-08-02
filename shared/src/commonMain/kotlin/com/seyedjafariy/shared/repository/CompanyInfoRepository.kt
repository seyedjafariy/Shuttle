package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.components.main.SpaceXAPI
import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.Data
import com.seyedjafariy.shared.model.dto.CompanyInfoDTO
import com.seyedjafariy.shared.model.mappers.toDomain
import com.seyedjafariy.shared.network.successBody
import com.seyedjafariy.shuttle.database.CompanyInfoQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOne
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

interface CompanyInfoRepository {
    fun observerCompanyInfo() : Flow<Data<CompanyInfo?>>
}

class CompanyInfoRepositoryImpl(
    private val api: SpaceXAPI,
    private val queries: CompanyInfoQueries,
) : CompanyInfoRepository {

    override fun observerCompanyInfo() = flow<Data<CompanyInfo?>> {
        val cachedValue = queries.get().executeAsOneOrNull()?.toDomain()

        emit(Data(cachedValue, true))

        val successful = updateCompanyInfo()

        if (successful) {
            emitAll(
                queries
                    .get()
                    .asFlow()
                    .mapToOne()
                    .map { it.toDomain() }
                    .map { Data(it, false) }
            )
        } else {
            emit(Data(cachedValue, false))
        }
    }

    private suspend fun updateCompanyInfo(): Boolean {
        val response = api.getCompanyInfo()

        if (response.isNotSuccessful) return false

        val companyInfo = response.successBody

        companyInfo.insertToDB()

        return true
    }

    private fun CompanyInfoDTO.insertToDB() {
        queries.insert(id, name, founder, founded.toLong(), employees.toLong(), launchSites.toLong(), valuation)

    }
}