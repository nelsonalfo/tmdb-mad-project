package com.example.tmdbmadproject.ui.popular

import com.example.tmdbmadproject.base.BaseAction
import com.example.tmdbmadproject.base.BaseIntention
import com.example.tmdbmadproject.base.BaseViewState
import com.example.tmdbmadproject.data.model.MovieResume

data class PopularMoviesViewState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val movies: List<MovieResume> = emptyList()
) : BaseViewState

sealed class PopularMoviesActions : BaseAction {
    object ErrorLoadingPopularMovies : PopularMoviesActions()
    object LoadingPopularMovies : PopularMoviesActions()
    data class SuccessLoadingPopularMovies(val movies: List<MovieResume>) : PopularMoviesActions()
}

sealed class PopularMoviesIntentions : BaseIntention {
    object LoadPopularMovies : PopularMoviesIntentions()
}