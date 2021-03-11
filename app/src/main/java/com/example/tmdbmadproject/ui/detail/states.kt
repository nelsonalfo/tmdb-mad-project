package com.example.tmdbmadproject.ui.detail

import com.example.tmdbmadproject.base.BaseAction
import com.example.tmdbmadproject.base.BaseIntention
import com.example.tmdbmadproject.base.BaseViewState
import com.example.tmdbmadproject.data.model.MovieDetail

data class MovieDetailViewState(
    val loading: Boolean = true,
    val error: Boolean = false,
    val movieTitle: String = "",
    val overView: String = "",
    val posterUrl: String = "",
    val backdropUrl: String = "",
    val ranking: Float = 0f,
    val votes: Int = 0,
    val genres: String = ""
) : BaseViewState

sealed class MovieDetailActions : BaseAction {
    object LoadingMovieDetails : MovieDetailActions()
    class SuccessLoadingMovieDetails(val movieDetail: MovieDetail) : MovieDetailActions()
    object ErrorLoadingMovieDetails : MovieDetailActions()
}

sealed class MovieDetailIntentions : BaseIntention {
    class LoadDetails(val movieId: Int) : MovieDetailIntentions()
}