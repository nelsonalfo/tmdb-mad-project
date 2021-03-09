package com.example.tmdbmadproject.data

import com.example.tmdbmadproject.data.model.TmdbConfiguration

class InMemoryCache {
    private val inMemoryCacheMap = mutableMapOf<String, Any>()

    fun setTmdbConfiguration(configuration: TmdbConfiguration) {
        inMemoryCacheMap[TmdbConfiguration::class.java.simpleName] = configuration
    }

    fun getTmdbConfiguration(): TmdbConfiguration? {
        return inMemoryCacheMap[TmdbConfiguration::class.java.simpleName] as TmdbConfiguration?
    }
}