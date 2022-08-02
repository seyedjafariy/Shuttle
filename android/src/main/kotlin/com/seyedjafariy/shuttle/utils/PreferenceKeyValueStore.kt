package com.seyedjafariy.shuttle.utils

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.seyedjafariy.shared.repository.KeyValueStore

class PreferenceKeyValueStore(
    private val sharedPreferences: SharedPreferences,
) : KeyValueStore {
    @SuppressLint("ApplySharedPref")
    override fun put(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .commit()
    }

    override fun get(key: String): String =
        sharedPreferences.getString(key, null)!!

    override fun contains(key: String): Boolean =
        sharedPreferences.contains(key)
}