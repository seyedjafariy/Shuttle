package com.seyedjafariy.shared.components.main

import com.seyedjafariy.shared.model.dto.CompanyInfoDTO
import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shared.model.dto.QueryRequestDTO
import com.seyedjafariy.shared.model.dto.QueryResponseDTO
import com.seyedjafariy.shared.network.Response

class FakeSpaceXAPI : SpaceXAPI {
    var companyInfoResponse : Response<CompanyInfoDTO>? = null
    var launchResponse : Map<QueryRequestDTO, Response<QueryResponseDTO<LaunchDTO>>> = emptyMap()

    override suspend fun getCompanyInfo(): Response<CompanyInfoDTO> =
        companyInfoResponse!!

    override suspend fun getLaunches(query: QueryRequestDTO): Response<QueryResponseDTO<LaunchDTO>> =
        launchResponse[query]!!
}