package com.example.tmdbmadproject.data.datasource

import com.example.tmdbmadproject.data.Constants
import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.MoviesResponse
import com.google.common.truth.Truth.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RemoteTmdbDataSourceTest {

    @MockK
    private lateinit var api: TmdbApi

    private lateinit var dataSource: RemoteTmdbDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        dataSource = RemoteTmdbDataSource(api)
    }

    @Test
    fun `WHEN getPopularMovies is called THEN return the list contained in the response`() = runBlockingTest {
        coEvery { api.getMovies(any(), any()) } returns MoviesResponse(
            page = 1, totalPages = 1, totalResults = 1,
            results = listOf(
                MovieResume(title = "Movie 1")
            )
        )

        val popularMovies = dataSource.getPopularMovies().single()

        coVerify { api.getMovies(Constants.MOST_POPULAR_MOVIES, Constants.API_KEY) }
        confirmVerified(api)
        assertThat(popularMovies).hasSize(1)
        assertThat(popularMovies[0].title).isEqualTo("Movie 1")
    }
}