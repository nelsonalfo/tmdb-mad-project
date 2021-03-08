package com.example.tmdbmadproject.data.api

import com.example.tmdbmadproject.data.model.MovieDetail
import com.example.tmdbmadproject.data.model.MoviesResponse
import com.example.tmdbmadproject.data.model.TmdbConfiguration

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface TmdbApi {
    @GET("discover/movie")
    suspend fun getMovies(@Query("sort_by") sortBy: String?, @Query("api_key") apiKey: String?): MoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String?): MoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String?): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int?, @Query("api_key") apiKey: String?): MovieDetail

    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String?): TmdbConfiguration
}