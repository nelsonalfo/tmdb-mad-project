package com.example.tmdbmadproject.data.datasource

import com.example.tmdbmadproject.data.Constants
import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.model.MovieResume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteTmdbDataSource @Inject constructor(private val api: TmdbApi) : TmdbDataSource {
    override fun getPopularMovies(): Flow<List<MovieResume>> = flow {
        val response = api.getMovies(sortBy = Constants.MOST_POPULAR_MOVIES, Constants.API_KEY)
        val movies = response.results ?: emptyList()

        emit(movies)
    }
}