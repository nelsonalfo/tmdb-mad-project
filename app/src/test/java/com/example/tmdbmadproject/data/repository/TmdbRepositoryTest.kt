package com.example.tmdbmadproject.data.repository

import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.MoviesResponse
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

@ExperimentalCoroutinesApi
class TmdbRepositoryTest {

    @MockK
    private lateinit var dataSource: TmdbDataSource

    private lateinit var repository: TmdbRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = TmdbRepository(dataSource)
    }

    @Test
    fun `WHEN getPopularMovies is called THEN call the data source and return the received Flow`() = runBlockingTest {
        coEvery { dataSource.getPopularMovies() } returns flowOf(
            listOf(
                MovieResume(title = "Movie 1")
            )
        )

        val popularMovies = repository.getPopularMovies().single()

        coVerify { dataSource.getPopularMovies() }
        confirmVerified(dataSource)
        assertThat(popularMovies).hasSize(1)
        assertThat(popularMovies[0].title).isEqualTo("Movie 1")
    }
}