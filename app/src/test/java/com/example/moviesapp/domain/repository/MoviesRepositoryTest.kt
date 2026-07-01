package com.example.moviesapp.domain.repository

import com.example.moviesapp.MOVIE_ID
import com.example.moviesapp.MOVIE_OVERVIEW
import com.example.moviesapp.MOVIE_POPULARITY
import com.example.moviesapp.MOVIE_RELEASE_DATE
import com.example.moviesapp.MOVIE_TITLE
import com.example.moviesapp.MOVIE_VOTE_AVERAGE
import com.example.moviesapp.data.datasource.api.MoviesApi
import com.example.moviesapp.data.datasource.localsource.MovieDao
import com.example.moviesapp.data.repository.MoviesRepositoryImpl
import com.example.moviesapp.generateMockMoviesApiResponse
import com.example.moviesapp.movieModelList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class MoviesRepositoryTest {

    @Mock
    lateinit var api: MoviesApi

    @Mock
    lateinit var dao: MovieDao

    private lateinit var sut: MoviesRepository
    private val response = generateMockMoviesApiResponse()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        sut = MoviesRepositoryImpl(api, dao)
    }

    @Test
    fun getPopularMoviesTest() = runTest {
        whenever(api.getPopularMovies()).thenReturn(response)

        val data = sut.getPopularMovies().first().first()

        assertEquals(data.movieId, MOVIE_ID)
        assertEquals(data.title, MOVIE_TITLE)
        assertEquals(data.overview, MOVIE_OVERVIEW)
        assertEquals(data.releaseDate, MOVIE_RELEASE_DATE)
        assertEquals(data.voteAverage, MOVIE_VOTE_AVERAGE, 0.0)
        assertEquals(data.popularity, MOVIE_POPULARITY, 0.0)

        verify(api).getPopularMovies()
    }

    @Test
    fun getLocalMoviesTest() = runTest {
        whenever(dao.getAllMovies()).thenReturn(movieModelList)

        val data = sut.getLocalMovies().first().first()

        assertEquals(data.movieId, MOVIE_ID)
        assertEquals(data.title, MOVIE_TITLE)
        assertEquals(data.overview, MOVIE_OVERVIEW)

        verify(dao).getAllMovies()
    }

    @Test
    fun getMovieByIdTest() = runTest {
        val movie = movieModelList.first()
        whenever(dao.getMovieById(MOVIE_ID)).thenReturn(movie)

        val data = sut.getMovieById(MOVIE_ID).first()

        assertEquals(data, movie)

        verify(dao).getMovieById(MOVIE_ID)
    }
}
