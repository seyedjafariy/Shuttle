package com.seyedjafariy.shuttle.ui.main.state

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.QuestionMark
import com.seyedjafariy.shared.components.main.MainComponent
import com.seyedjafariy.shared.model.CompanyInfo
import com.seyedjafariy.shared.model.Launch
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.utils.StringState
import java.text.NumberFormat
import java.util.*

fun MainComponent.State.toState() = MainState(
    companyInfo.content?.toState(),
    launches.content.map { it.toState() },
    launchesLoadSpec.toFilterState(),
)

fun CompanyInfo.toState() = CompanyInfoState(
    StringState(
        R.string.company_info,
        listOf(
            name,
            founder,
            founded,
            employees,
            launchSites,
            numberFormat.format(valuation),
        )
    )
)

fun Launch.toState() = LaunchState(
    id,
    name,
    image = if (smallImage != null) smallImage else largeImage,
    successIcon = successState.toIcon(),
    daysDistanceTitle = getDaysDistanceTitle(),
    dateValue = getDateValue(),
    rocketValue = getRocketValue(),
    fromTodayValue = fromToday.toString(),
    socialLinks = getSocialLinks(),
)

private fun Launch.getRocketValue(): StringState {
    return StringState(
        R.string.rocket_name_type_template,
        listOf(
            rocket.name,
            rocket.type,
        )
    )
}

private fun Launch.getDaysDistanceTitle(): StringState {
    val titleResource = if (upcoming) {
        R.string.days_from_now
    } else {
        R.string.days_since_now
    }

    return StringState(
        titleResource
    )
}

private fun Launch.getDateValue(): StringState {
    return StringState(
        R.string.date_time_template,
        listOf(
            "${date.dayOfMonth}/${date.monthNumber}/${date.year}",
            "${date.hour}:${date.minute}"
        )
    )
}

private fun Launch.SuccessState.toIcon() = when (this) {
    Launch.SuccessState.NOT_LAUNCHED -> Icons.Default.QuestionMark
    Launch.SuccessState.SUCCESSFUL -> Icons.Default.Check
    Launch.SuccessState.FAILED -> Icons.Default.Close
}

private fun Launch.getSocialLinks() = buildSet {
    if (youtube != null) {
        add(LaunchState.SocialLink.YT(youtube!!))
    }
    if (wikipedia != null) {
        add(LaunchState.SocialLink.Wiki(wikipedia!!))
    }
    if (article != null) {
        add(LaunchState.SocialLink.Article(article!!))
    }
}

private val numberFormat = NumberFormat.getCurrencyInstance().apply {
    maximumFractionDigits = 0
    currency = Currency.getInstance("USD")
}
