package com.seyedjafariy.shared.model.mappers

import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.dto.CompanyInfoDTO
import com.seyedjafariy.shuttle.database.Company_info

fun CompanyInfoDTO.toDomain() = CompanyInfo(
    name = name,
    founder = founder,
    founded = founded,
    employees = employees,
    launchSites = launchSites,
    valuation = valuation,
)

fun Company_info.toDomain() = CompanyInfo(
    name,
    founder,
    founded.toInt(),
    employees.toInt(),
    launchSites.toInt(),
    valuation
)