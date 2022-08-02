package com.seyedjafariy.shared.repository

class MemoryKeyValueStore : KeyValueStore {

    val cache = mutableMapOf<String, String>()

    override fun put(key: String, value: String) {
        cache[key] = value
    }

    override fun get(key: String): String {
        return cache[key]!!
    }

    override fun contains(key: String): Boolean {
        return cache.contains(key)
    }
}