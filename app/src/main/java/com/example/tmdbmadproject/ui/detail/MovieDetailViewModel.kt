package com.example.tmdbmadproject.ui.detail

import androidx.lifecycle.viewModelScope
import com.example.tmdbmadproject.base.BaseViewModel
import com.example.tmdbmadproject.data.model.formatted
import com.example.tmdbmadproject.data.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: TmdbRepository) :
    BaseViewModel<MovieDetailViewState, MovieDetailActions, MovieDetailIntentions>(MovieDetailViewState()) {

    override fun sendIntention(intention: MovieDetailIntentions) {
        when (intention) {
            is MovieDetailIntentions.LoadDetails -> if (state.loading || state.error) {
                loadMovieDetails(intention.movieId)
            }
        }
    }

    private fun loadMovieDetails(movieId: Int) {
        sendAction(MovieDetailActions.LoadingMovieDetails)

        viewModelScope.launch {
            repository.getMovieDetail(movieId)
                .catch { sendAction(MovieDetailActions.ErrorLoadingMovieDetails) }
                .collect { movieDetail -> sendAction(MovieDetailActions.SuccessLoadingMovieDetails(movieDetail)) }
        }
    }

    override fun onReduceState(action: MovieDetailActions): MovieDetailViewState {
        return when (action) {
            is MovieDetailActions.LoadingMovieDetails -> state.copy(loading = true, error = false)
            is MovieDetailActions.ErrorLoadingMovieDetails -> state.copy(loading = false, error = true)
            is MovieDetailActions.SuccessLoadingMovieDetails -> with(action.movieDetail) {
                state.copy(
                    loading = false,
                    error = false,
                    movieTitle = title,
                    overView = overview,
                    posterUrl = posterUrl,
                    backdropUrl = backdropUrl,
                    ranking = voteAverage.toFloat(),
                    votes = voteCount,
                    genres = if (genres.isEmpty()) "N/A" else genres.formatted()
                )
            }
        }
    }
}