package com.example.tmdbmadproject.ui.popular

import androidx.lifecycle.viewModelScope
import com.example.tmdbmadproject.base.BaseViewModel
import com.example.tmdbmadproject.data.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val repository: TmdbRepository) :
    BaseViewModel<PopularMoviesViewState, PopularMoviesActions, PopularMoviesIntentions>(PopularMoviesViewState()) {

    override fun sendIntention(intention: PopularMoviesIntentions) {
        when (intention) {
            is PopularMoviesIntentions.LoadPopularMovies -> if (state.movies.isEmpty()) {
                searchPopularMovies()
            }
        }
    }

    private fun searchPopularMovies() {
        sendAction(PopularMoviesActions.LoadingPopularMovies)

        viewModelScope.launch {
            repository.getPopularMovies()
                .catch { sendAction(PopularMoviesActions.ErrorLoadingPopularMovies) }
                .collect { popularMovies -> sendAction(PopularMoviesActions.SuccessLoadingPopularMovies(popularMovies)) }
        }
    }

    override fun onReduceState(action: PopularMoviesActions): PopularMoviesViewState {
        return when (action) {
            is PopularMoviesActions.LoadingPopularMovies -> state.copy(isLoading = true, isError = false, movies = emptyList())
            is PopularMoviesActions.ErrorLoadingPopularMovies -> state.copy(isLoading = false, isError = true, movies = emptyList())
            is PopularMoviesActions.SuccessLoadingPopularMovies -> state.copy(isLoading = false, isError = false, movies = action.movies)
        }
    }
}