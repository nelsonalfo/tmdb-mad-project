package com.example.tmdbmadproject.data.datasource

import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.TmdbConfiguration
import kotlinx.coroutines.flow.Flow


interface TmdbDataSource {
    fun getPopularMovies(): Flow<List<MovieResume>>

    fun getTmdbConfiguration(): Flow<TmdbConfiguration>
}