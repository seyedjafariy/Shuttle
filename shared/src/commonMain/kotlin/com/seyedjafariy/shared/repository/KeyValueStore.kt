package com.seyedjafariy.shared.repository

interface KeyValueStore {

    fun put(key : String, value :String)
    fun get(key : String) : String
    fun contains(key : String) : Boolean
}