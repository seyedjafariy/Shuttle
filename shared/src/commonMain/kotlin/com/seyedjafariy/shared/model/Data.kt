package com.seyedjafariy.shared.model

data class Data<T>(
    val content : T,
    val loading : Boolean = false,
)