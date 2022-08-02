package com.seyedjafariy.shuttle.ui.main.state

sealed interface DateFilterState {
    object Off : DateFilterState
    data class On(val date : String) : DateFilterState
}