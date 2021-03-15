package com.example.tmdbmadproject.data.repository

import com.example.tmdbmadproject.data.datasource.TmdbDataSource
import com.example.tmdbmadproject.data.model.Images
import com.example.tmdbmadproject.data.model.MovieDetail
import com.example.tmdbmadproject.data.model.MovieResume
import com.example.tmdbmadproject.data.model.TmdbConfiguration
import com.google.common.truth.Truth.assertThat
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
    fun `WHEN getPopularMovies is called THEN call the data source to get the popular movies and the image configurations`() = runBlockingTest {
        coEvery { dataSource.getPopularMovies() } returns flowOf(listOf(MovieResume()))
        coEvery { dataSource.getTmdbConfiguration() } returns flowOf(TmdbConfiguration())

        repository.getPopularMovies().single()

        coVerify { dataSource.getPopularMovies() }
        coVerify { dataSource.getTmdbConfiguration() }
        confirmVerified(dataSource)
    }

    @Test
    fun `GIVEN the data source return movies and the image configurations WHEN getPopularMovies is called THEN return the movies with its poster URLs`() = runBlockingTest {
        coEvery { dataSource.getPopularMovies() } returns flowOf(listOf(MovieResume(title = "Movie 1", posterPath = "/posterpath.jpg")))
        coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
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

        val popularMovies = repository.getPopularMovies().single()

        assertThat(popularMovies).hasSize(1)
        assertThat(popularMovies[0].title).isEqualTo("Movie 1")
        assertThat(popularMovies[0].posterUrl).isEqualTo("https://image.tmdb.org/t/p/w500/posterpath.jpg")
    }

    @Test
    fun `GIVEN the data source return the popular movies and no image configurations for posters WHEN getPopularMovies is called THEN return the movies with empty poster URLs`() {
        runBlockingTest {
            coEvery { dataSource.getPopularMovies() } returns flowOf(listOf(MovieResume(title = "Movie 1", posterPath = "/posterpath.jpg")))
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        posterSizes = null,
                        backdropSizes = listOf("w300", "w780", "w1280", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val popularMovies = repository.getPopularMovies().single()

            assertThat(popularMovies).hasSize(1)
            assertThat(popularMovies[0].title).isEqualTo("Movie 1")
            assertThat(popularMovies[0].posterUrl).isEmpty()
        }
    }

    @Test
    fun `GIVEN the data source return movies and null image configurations WHEN getPopularMovies is called THEN return the movies with empty poster URLs`() = runBlockingTest {
        coEvery { dataSource.getPopularMovies() } returns flowOf(listOf(MovieResume(title = "Movie 1", posterPath = "/posterpath.jpg")))
        coEvery { dataSource.getTmdbConfiguration() } returns flowOf(TmdbConfiguration(images = null))

        val popularMovies = repository.getPopularMovies().single()

        assertThat(popularMovies).hasSize(1)
        assertThat(popularMovies[0].title).isEqualTo("Movie 1")
        assertThat(popularMovies[0].posterUrl).isEmpty()
    }

    @Test
    fun `GIVEN the data source return movies and the image configurations with no the default poster size WHEN getPopularMovies is called THEN return the movies with its poster URLs with the first size available`() {
        runBlockingTest {
            coEvery { dataSource.getPopularMovies() } returns flowOf(listOf(MovieResume(title = "Movie 1", posterPath = "/posterpath.jpg")))
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        backdropSizes = listOf("w300", "w780", "w1280", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        posterSizes = listOf("w92", "w154", "w185", "w342", "w780", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val popularMovies = repository.getPopularMovies().single()

            assertThat(popularMovies).hasSize(1)
            assertThat(popularMovies[0].title).isEqualTo("Movie 1")
            assertThat(popularMovies[0].posterUrl).isEqualTo("https://image.tmdb.org/t/p/w92/posterpath.jpg")
        }
    }

    @Test
    fun `WHEN getMovieDetail is called THEN call the data source to get the movie default and the image configurations`() = runBlockingTest {
        val movieId = 12345
        coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(MovieDetail())
        coEvery { dataSource.getTmdbConfiguration() } returns flowOf(TmdbConfiguration())

        repository.getMovieDetail(movieId).single()

        coVerify { dataSource.getMovieDetail(movieId) }
        coVerify { dataSource.getTmdbConfiguration() }
        confirmVerified(dataSource)
    }

    @Test
    fun `GIVEN the data source return the movie default and the image configurations WHEN getMovieDetail is called THEN return the movie detail with its poster and backdrop URL`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(
                    title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
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

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEqualTo("https://image.tmdb.org/t/p/w500/posterpath.jpg")
            assertThat(movieDetail.backdropUrl).isEqualTo("https://image.tmdb.org/t/p/w1280/backdroppath.jpg")
        }
    }

    @Test
    fun `GIVEN the data source return the movie detail and the no image configurations for posters WHEN getMovieDetail is called THEN return the movie with empty poster URL`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(
                    title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        posterSizes = null,
                        backdropSizes = listOf("w300", "w780", "w1280", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEmpty()
            assertThat(movieDetail.backdropUrl).isEqualTo("https://image.tmdb.org/t/p/w1280/backdroppath.jpg")
        }
    }

    @Test
    fun `GIVEN the data source return the movie detail and no image configurations for backdrops WHEN getMovieDetail is called THEN return the movie with empty backdrop URL`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                        backdropSizes = null,
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEqualTo("https://image.tmdb.org/t/p/w500/posterpath.jpg")
            assertThat(movieDetail.backdropUrl).isEmpty()
        }
    }

    @Test
    fun `GIVEN the data source return the movie detail and null image configurations WHEN getMovieDetail is called THEN return the movie detail with empty poster and backdrop URLs`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(TmdbConfiguration(images = null))
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(
                    title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEmpty()
            assertThat(movieDetail.backdropUrl).isEmpty()
        }
    }

    @Test
    fun `GIVEN the data source return the movie detail and the image configurations with no the default poster size WHEN getMovieDetail is called THEN return the movie detail with its poster URL with the first size available`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(
                    title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        backdropSizes = listOf("w300", "w780", "w1280", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        posterSizes = listOf("w92", "w154", "w185", "w342", "w780", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEqualTo("https://image.tmdb.org/t/p/w92/posterpath.jpg")
            assertThat(movieDetail.backdropUrl).isEqualTo("https://image.tmdb.org/t/p/w1280/backdroppath.jpg")
        }
    }

    @Test
    fun `GIVEN the data source return the movie detail and the image configurations with no the default backdrop size WHEN getMovieDetail is called THEN return the movie detail with its backdrop URL with the first size available`() {
        runBlockingTest {
            val movieId = 12345
            coEvery { dataSource.getMovieDetail(movieId) } returns flowOf(
                MovieDetail(
                    title = "Movie 1",
                    posterPath = "/posterpath.jpg",
                    backdropPath = "/backdroppath.jpg"
                )
            )
            coEvery { dataSource.getTmdbConfiguration() } returns flowOf(
                TmdbConfiguration(
                    images = Images(
                        baseUrl = "http://image.tmdb.org/t/p/",
                        secureBaseUrl = "https://image.tmdb.org/t/p/",
                        backdropSizes = listOf("w300", "w780", "original"),
                        posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                        logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                        profileSizes = listOf("w45", "w185", "h632", "original"),
                        stillSizes = listOf("w92", "w185", "w300", "original")
                    )
                )
            )

            val movieDetail = repository.getMovieDetail(movieId).single()

            assertThat(movieDetail.title).isEqualTo("Movie 1")
            assertThat(movieDetail.posterUrl).isEqualTo("https://image.tmdb.org/t/p/w500/posterpath.jpg")
            assertThat(movieDetail.backdropUrl).isEqualTo("https://image.tmdb.org/t/p/w300/backdroppath.jpg")
        }
    }
}