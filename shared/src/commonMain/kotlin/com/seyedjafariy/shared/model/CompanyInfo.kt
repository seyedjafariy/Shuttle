package com.seyedjafariy.shared.model

data class CompanyInfo(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launchSites: Int,
    val valuation: Long,
)