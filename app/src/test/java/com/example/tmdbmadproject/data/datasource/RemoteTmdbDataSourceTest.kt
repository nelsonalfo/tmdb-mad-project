package com.example.tmdbmadproject.data.datasource

import com.example.tmdbmadproject.data.Constants
import com.example.tmdbmadproject.data.InMemoryCache
import com.example.tmdbmadproject.data.api.TmdbApi
import com.example.tmdbmadproject.data.model.*
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class RemoteTmdbDataSourceTest {

    @MockK
    private lateinit var api: TmdbApi

    private lateinit var inMemoryCache: InMemoryCache

    private lateinit var dataSource: RemoteTmdbDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        inMemoryCache = InMemoryCache()
        dataSource = RemoteTmdbDataSource(api, inMemoryCache)
    }

    @Test
    fun `GIVEN the api return a successful response WHEN getPopularMovies is called THEN return the list contained in the response`() = runBlockingTest {
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

    @Test
    fun `GIVEN the api return an exception WHEN getPopularMovies is called THEN return the exception`() = runBlockingTest {
        coEvery { api.getMovies(any(), any()) } throws IOException()

        try {
            dataSource.getPopularMovies().single()
        } catch (exception: Exception) {
            assertThat(exception).isInstanceOf(IOException::class.java)
        }
    }

    @Test
    fun `GIVEN the api return a successful response WHEN getMovieDetail is called THEN return the response`() = runBlockingTest {
        val movieId = 12345
        coEvery { api.getMovieDetail(any(), any()) } returns MovieDetail(title = "Movie 1")

        val movieDetail = dataSource.getMovieDetail(movieId).single()

        coVerify { api.getMovieDetail(movieId, Constants.API_KEY) }
        confirmVerified(api)
        assertThat(movieDetail.title).isEqualTo("Movie 1")
    }

    @Test
    fun `GIVEN there is no configuration in cache and the api return a successful response WHEN getTmdbConfiguration is called THEN return the response and save it in cache`() {
        runBlockingTest {
            coEvery { api.getConfiguration(any()) } returns TmdbConfiguration(
                images = Images(
                    baseUrl = "http://image.tmdb.org/t/p/",
                    secureBaseUrl = "https://image.tmdb.org/t/p/",
                    backdropSizes = listOf("w300", "w780", "w1280", "original"),
                    logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                    posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                    profileSizes = listOf("w45", "w185", "h632", "original"),
                    stillSizes = listOf("w92", "w185", "w300", "original")
                )
            )

            val tmdbConfiguration = dataSource.getTmdbConfiguration().single()

            coVerify { api.getConfiguration(Constants.API_KEY) }
            confirmVerified(api)
            assertThat(inMemoryCache.getTmdbConfiguration()).isEqualTo(tmdbConfiguration)
            assertThat(tmdbConfiguration.images).isNotNull()
            with(tmdbConfiguration.images!!) {
                assertThat(baseUrl).isEqualTo("http://image.tmdb.org/t/p/")
                assertThat(posterSizes).hasSize(7)
            }
        }
    }

    @Test
    fun `GIVEN there is a configuration in memory cache WHEN getTmdbConfiguration is called THEN return the configuration already store in cache`() {
        runBlockingTest {
            inMemoryCache.setTmdbConfiguration(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        backdropSizes = listOf("w300", "w780", "w1280", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val tmdbConfiguration = dataSource.getTmdbConfiguration().single()

            coVerify { api.getConfiguration(Constants.API_KEY) wasNot Called }
            confirmVerified(api)
            assertThat(inMemoryCache.getTmdbConfiguration()).isEqualTo(tmdbConfiguration)
            assertThat(tmdbConfiguration.images).isNotNull()
            with(tmdbConfiguration.images!!) {
                assertThat(baseUrl).isEqualTo("http://image.tmdb.org/t/p/")
                assertThat(posterSizes).hasSize(7)
            }
        }
    }
}