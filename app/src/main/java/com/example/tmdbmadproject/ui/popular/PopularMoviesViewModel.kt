package com.example.tmdbmadproject.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.repository.TmdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(private val repository: TmdbRepository) : ViewModel() {
    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    private var state: ViewState by Delegates.observable(initialValue = ViewState()) { _, _, new -> _viewState.value = new }

    fun sendIntention(intention: ViewIntention) {
        when (intention) {
            ViewIntention.LoadPopularMovies -> {
                if (state.movies.isEmpty()) {
                    searchPopularMovies()
                }
            }
        }
    }

    private fun sendAction(action: ViewAction) {
        state = onReduceState(action)
    }

    private fun onReduceState(action: ViewAction): ViewState {
        return when (action) {
            is ViewAction.LoadingPopularMovies -> state.copy(isLoading = true, isError = false, movies = emptyList())
            is ViewAction.ErrorLoadingPopularMovies -> state.copy(isLoading = false, isError = true, movies = emptyList())
            is ViewAction.SuccessLoadingPopularMovies -> state.copy(isLoading = false, isError = false, movies = action.movies)
        }
    }

    private fun searchPopularMovies() {
        sendAction(ViewAction.LoadingPopularMovies)

        viewModelScope.launch {
            repository.getPopularMovies()
                .catch { sendAction(ViewAction.ErrorLoadingPopularMovies) }
                .collect { popularMovies -> sendAction(ViewAction.SuccessLoadingPopularMovies(popularMovies)) }
        }
    }
}

data class ViewState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val movies: List<MovieResume> = emptyList()
) : BaseViewState

sealed class ViewAction : BaseViewAction {
    object ErrorLoadingPopularMovies : ViewAction()
    object LoadingPopularMovies : ViewAction()
    data class SuccessLoadingPopularMovies(val movies: List<MovieResume>) : ViewAction()
}

sealed class ViewIntention : BaseViewIntention {
    object LoadPopularMovies : ViewIntention()
}

interface BaseViewState

interface BaseViewAction

interface BaseViewIntention