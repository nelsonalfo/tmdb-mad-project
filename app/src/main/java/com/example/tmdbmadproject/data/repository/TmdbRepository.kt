package com.example.tmdbmadproject.data.repository

import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import com.example.tmdbmadproject.data.model.MovieResume
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TmdbRepository @Inject constructor(private val popularMoviesDataSource: TmdbDataSource) {

    fun getPopularMovies(): Flow<List<MovieResume>> = popularMoviesDataSource.getPopularMovies()
}