package com.example.tmdbmadproject.ui.popular

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tmdbmadproject.MainCoroutineRule
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.repository.TmdbRepository
import com.example.tmdbmadproject.observeForTesting
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class PopularMoviesViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var repository: TmdbRepository

    private lateinit var viewModel: PopularMoviesViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        viewModel = PopularMoviesViewModel(repository)
    }

    @Test
    fun `GIVEN there is no movies loaded WHEN sendIntention is called THEN call the repository to get the popular movies`() {
        coEvery { repository.getPopularMovies() } returns flowOf(listOf(MovieResume(title = "Movie 1"), MovieResume(title = "Movie 2")))

        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)

        coVerify { repository.getPopularMovies() }
        confirmVerified(repository)
    }

    @Test
    fun `GIVEN there is movies loaded WHEN sendIntention is called THEN dont call the repository`() {
        viewModel.changeState(PopularMoviesViewState(isLoading = false, isError = false, movies = listOf(MovieResume(title = "Movie 1"))))

        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)

        coVerify { repository wasNot Called }
        confirmVerified(repository)
    }

    @Test
    fun `GIVEN there is no movies loaded and the repository return movies WHEN sendIntention is called THEN change the view state`() {
        val movies = listOf(MovieResume(title = "Movie 1"), MovieResume(title = "Movie 2"))
        coEvery { repository.getPopularMovies() } returns flowOf(movies)

        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)

        assertThat(viewModel.viewState.value!!).isEqualTo(PopularMoviesViewState(isLoading = false, isError = false, movies = movies))
    }

    @Test
    fun `GIVEN there is no movies loaded and the repository return error in the flow WHEN sendIntention is called THEN change the view state`() {
        coEvery { repository.getPopularMovies() } returns flow { throw IOException() }

        viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)

        assertThat(viewModel.viewState.value!!).isEqualTo(PopularMoviesViewState(isLoading = false, isError = true, movies = emptyList()))
    }

    @Test
    fun `GIVEN there is no movies loaded and the repository return movies WHEN sendIntention is called THEN change the view state from loading to loaded`() {
        val movies = listOf(MovieResume(title = "Movie 1"), MovieResume(title = "Movie 2"))

        viewModel.viewState.observeForTesting { spiedObserver, viewStates ->
            coEvery { repository.getPopularMovies() } returns flowOf(movies)

            viewModel.sendIntention(PopularMoviesIntentions.LoadPopularMovies)

            verify { spiedObserver.onChanged(capture(viewStates)) }
            assertThat(viewStates[0]).isEqualTo(PopularMoviesViewState(isLoading = true, isError = false, movies = emptyList()))
            assertThat(viewStates[1]).isEqualTo(PopularMoviesViewState(isLoading = false, isError = false, movies = movies))
        }
    }
}