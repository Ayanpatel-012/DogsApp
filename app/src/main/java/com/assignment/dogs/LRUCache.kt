package com.assignment.dogs

import android.content.Context
import android.content.SharedPreferences

object LRUCache {
    private const val maxCapacity = 20
    private lateinit var cache: ArrayList<String>
    fun initialize() {
        cache = ArrayList(SharedPrefManager.loadCacheData())
    }

    fun put(value: String) {
        if (value.isNullOrEmpty()) return
        if (cache.size >= maxCapacity) {
            cache.removeAt(maxCapacity - 1)
        }

        cache.add(0, value)
        SharedPrefManager.saveCacheData(cache)
    }

    fun clearCache() {
        cache = ArrayList()
        SharedPrefManager.clearCacheData()
    }

    fun get(): List<String> {
        return cache
    }

}

object SharedPrefManager {
    private const val PREF_NAME = "MyCache"
    private const val KEY_CACHE_DATA = "cacheData"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveCacheData(cacheData: List<String>) {
        val serializedData = cacheData.joinToString(",")
        sharedPreferences.edit().putString(KEY_CACHE_DATA, serializedData).apply()
    }

    fun loadCacheData(): List<String> {
        val cacheData = sharedPreferences.getString(KEY_CACHE_DATA, "")
        return cacheData?.split(",") ?: emptyList()
    }

    fun clearCacheData() {
        sharedPreferences.edit().remove(KEY_CACHE_DATA).apply()
    }
}