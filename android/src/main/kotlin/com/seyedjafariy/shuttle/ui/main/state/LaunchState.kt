package com.seyedjafariy.shuttle.ui.main.state

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.YoutubeSearchedFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.seyedjafariy.shuttle.R
import com.seyedjafariy.shuttle.utils.StringState

data class LaunchState(
    val id: String,
    val name: String,
    val image: String?,
    val successIcon: ImageVector,
    val daysDistanceTitle: StringState,
    val dateValue: StringState,
    val rocketValue: StringState,
    val fromTodayValue: String,
    val socialLinks: Set<SocialLink>,
) {
    sealed interface SocialLink {

        val link: String

        val icon: Int

        data class YT(override val link: String) : SocialLink {

            override val icon: Int
                get() = R.drawable.ic_youtube
        }

        data class Wiki(override val link: String) : SocialLink {

            override val icon: Int
                get() = R.drawable.ic_wikipedia
        }

        data class Article(override val link: String) : SocialLink {

            override val icon: Int
                get() = R.drawable.ic_news
        }
    }
}