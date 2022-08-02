package com.seyedjafariy.shared.repository

import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class FakeCompanyInfoRepository : CompanyInfoRepository {

    var data: List<Data<CompanyInfo?>> = emptyList()

    override fun observerCompanyInfo(): Flow<Data<CompanyInfo?>> =
        data.asFlow()
}