package com.seyedjafariy.shuttle.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.seyedjafariy.shuttle.sample.createLaunchState
import com.seyedjafariy.shuttle.ui.main.state.LaunchState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@RunWith(AndroidJUnit4::class)
class LaunchRowTest {

    @get:Rule
    val composeActivityRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun correctly_shows_launch() {
        val sampleLaunch = createLaunchState()

        composeActivityRule.setContent {
            LaunchRow(launch = sampleLaunch, openLaunchOptions = {})
        }

        composeActivityRule.onNodeWithText(sampleLaunch.name).assertIsDisplayed()

    }

    @Test
    fun when_social_links_are_not_empty_clicks_on_row_fire_callback() {
        val latch = CountDownLatch(1)

        val sampleLaunch = createLaunchState(
            socialLinks = setOf(LaunchState.SocialLink.YT("sample"))
        )

        composeActivityRule.setContent {
            LaunchRow(launch = sampleLaunch, openLaunchOptions = {
                Assert.assertEquals(sampleLaunch, it)
                latch.countDown()
            })
        }

        composeActivityRule.onNodeWithText(sampleLaunch.name).performClick()

        latch.await()
    }
}