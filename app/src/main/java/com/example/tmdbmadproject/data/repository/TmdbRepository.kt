package com.example.tmdbmadproject.data.repository

import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import com.example.tmdbmadproject.data.model.MovieDetail
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.generateBackdropUrl
import com.example.tmdbmadproject.data.model.generatePosterUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class TmdbRepository @Inject constructor(private val dataSource: TmdbDataSource) {

    fun getPopularMovies(): Flow<List<MovieResume>> = dataSource.getPopularMovies()
        .zip(dataSource.getTmdbConfiguration()) { movies, configuration ->
            movies.onEach { movieResume -> movieResume.posterUrl = configuration.images?.generatePosterUrl(movieResume.posterPath) ?: "" }
        }

    fun getMovieDetail(movieId: Int): Flow<MovieDetail> = dataSource.getMovieDetail(movieId)
        .zip(dataSource.getTmdbConfiguration()) { movieDetail, tmdbConfiguration ->
            movieDetail.apply {
                posterUrl = tmdbConfiguration.images?.generatePosterUrl(posterPath) ?: ""
                backdropUrl = tmdbConfiguration.images?.generateBackdropUrl(backdropPath) ?: ""
            }
        }
}