package com.example.tmdbmadproject.di

import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.datasource.RemoteTmdbDataSource
import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesDataSource(api: TmdbApi): TmdbDataSource {
        return RemoteTmdbDataSource(api)
    }
}