package com.example.tmdbmadproject.data.datasource

import com.example.tmdbmadproject.data.Constants
import com.example.tmdbmadproject.data.InMemoryCache
import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.model.MovieDetail
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.TmdbConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteTmdbDataSource @Inject constructor(private val api: TmdbApi, private val inMemoryCache: InMemoryCache) : TmdbDataSource {
    override fun getPopularMovies(): Flow<List<MovieResume>> = flow {
        val response = api.getMovies(sortBy = Constants.MOST_POPULAR_MOVIES, apiKey = Constants.API_KEY)
        emit(response.results)
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetail> = flow {
        val movieDetail = api.getMovieDetail(movieId, Constants.API_KEY)
        emit(movieDetail)
    }

    override fun getTmdbConfiguration(): Flow<TmdbConfiguration> = flow {
        if (inMemoryCache.getTmdbConfiguration() == null) {
            val configuration = api.getConfiguration(Constants.API_KEY)
            inMemoryCache.setTmdbConfiguration(configuration)
        }

        val tmdbConfiguration = inMemoryCache.getTmdbConfiguration() ?: TmdbConfiguration()

        emit(tmdbConfiguration)
    }
}

