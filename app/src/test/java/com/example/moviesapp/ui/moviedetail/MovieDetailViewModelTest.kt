package com.example.moviesapp.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MOVIE_ID
import com.example.moviesapp.MOVIE_TITLE
import com.example.moviesapp.domain.repository.MoviesRepository
import com.example.moviesapp.domain.usecases.GetMovieByIdUseCase
import com.example.moviesapp.movieModelList
import com.example.moviesapp.ui.moviesscreen.CoroutinesTestRule
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MovieDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var repository: MoviesRepository

    private lateinit var getMovieByIdUseCase: GetMovieByIdUseCase
    private lateinit var sut: MovieDetailViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getMovieByIdUseCase = GetMovieByIdUseCase(repository)
    }

    @After
    fun tearDown() {
        if (::sut.isInitialized) sut.viewModelScope.cancel()
    }

    @Test
    fun loadsMovieByIdFromSavedStateHandle() = runBlocking {
        val movie = movieModelList.first()
        whenever(repository.getMovieById(MOVIE_ID)).thenReturn(flow { emit(movie) })

        val savedStateHandle = SavedStateHandle(mapOf("movieId" to MOVIE_ID))
        sut = MovieDetailViewModel(getMovieByIdUseCase, savedStateHandle)

        withTimeout(2000) {
            while (sut.movie.value == null) {
                delay(10)
            }
        }

        assertEquals(MOVIE_TITLE, sut.movie.value?.title)
    }
}
