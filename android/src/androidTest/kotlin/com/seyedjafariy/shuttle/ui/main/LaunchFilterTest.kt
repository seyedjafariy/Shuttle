package com.seyedjafariy.shuttle.ui.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.seyedjafariy.shuttle.sample.createLaunchFilterState
import com.seyedjafariy.shuttle.ui.main.state.DateSortingState
import com.seyedjafariy.shuttle.ui.main.state.LaunchOperationState
import junit.framework.Assert.assertEquals
import com.seyedjafariy.shuttle.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LaunchFilterTest {

    @get:Rule
    val composeActivityRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun when_DESC_sort_is_submitted_correct_filterState_is_returned() {
        val filterState = createLaunchFilterState()

        composeActivityRule.setContent {
            LaunchFilterDialog(initialState = filterState, onDismiss = { }, onSubmit = {
                assertEquals(filterState.copy(sortingState = DateSortingState.DESC), it)
            })
        }

        composeActivityRule.onNodeWithTag("DESC-BUTTON").performClick()
        composeActivityRule.onNodeWithText("Submit").performClick()
    }

    @Test
    fun when_OperationState_OnlySuccessful_is_submitted_correct_filterState_is_returned() {
        with(composeActivityRule) {
            val filterState = createLaunchFilterState()

            setContent {
                LaunchFilterDialog(initialState = filterState, onDismiss = { }, onSubmit = {
                    assertEquals(filterState.copy(operationState = LaunchOperationState.ONLY_SUCCESSFUL), it)
                })
            }

            onNodeWithText(activity.getString(R.string.only_successful)).performClick()
            onNodeWithText(activity.getString(R.string.submit)).performClick()
        }
    }
}