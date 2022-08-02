package com.seyedjafariy.shared.components.main

import com.seyedjafariy.shared.model.dto.CompanyInfoDTO
import com.seyedjafariy.shared.model.dto.LaunchDTO
import com.seyedjafariy.shared.model.dto.QueryRequestDTO
import com.seyedjafariy.shared.model.dto.QueryResponseDTO
import com.seyedjafariy.shared.network.Response
import com.seyedjafariy.shared.network.addJsonBody
import com.seyedjafariy.shared.network.defaultRequest
import com.seyedjafariy.shared.network.executeRequest
import io.ktor.client.*
import io.ktor.http.*

interface SpaceXAPI {
    suspend fun getCompanyInfo(): Response<CompanyInfoDTO>
    suspend fun getLaunches(query: QueryRequestDTO): Response<QueryResponseDTO<LaunchDTO>>
}

class SpaceXAPIImpl(
    private val client: HttpClient
) : SpaceXAPI {

    override suspend fun getCompanyInfo(): Response<CompanyInfoDTO> = client.executeRequest<CompanyInfoDTO> {
        defaultRequest()
        method = HttpMethod.Get
        url {
            encodedPath = "v4/company"
        }
    }

    override suspend fun getLaunches(query: QueryRequestDTO): Response<QueryResponseDTO<LaunchDTO>> = client.executeRequest<QueryResponseDTO<LaunchDTO>> {
        defaultRequest()
        method = HttpMethod.Post
        url {
            encodedPath = "v4/launches/query"
        }
        addJsonBody(query)
    }
}
