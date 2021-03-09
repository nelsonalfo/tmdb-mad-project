package com.example.tmdbmadproject.di

import com.example.tmdbmadproject.data.InMemoryCache
import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.datasource.RemoteTmdbDataSource
import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesDataSource(api: TmdbApi, inMemoryCache: InMemoryCache): TmdbDataSource {
        return RemoteTmdbDataSource(api, inMemoryCache)
    }

    @Provides
    @Singleton
    fun providesInMemoryCache(): InMemoryCache {
        return InMemoryCache()
    }
}