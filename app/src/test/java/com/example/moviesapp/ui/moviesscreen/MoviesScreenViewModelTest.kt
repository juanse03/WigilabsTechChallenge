package com.example.moviesapp.ui.moviesscreen

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.R
import com.example.moviesapp.domain.repository.MoviesRepository
import com.example.moviesapp.domain.usecases.FetchLocalMoviesUseCase
import com.example.moviesapp.domain.usecases.FetchPopularMoviesUseCase
import com.example.moviesapp.movieModelList
import com.example.moviesapp.ui.state.MoviesScreenState
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class MoviesScreenViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var repository: MoviesRepository

    @Mock
    lateinit var context: Context

    private lateinit var fetchPopularMoviesUseCase: FetchPopularMoviesUseCase
    private lateinit var fetchLocalMoviesUseCase: FetchLocalMoviesUseCase

    private lateinit var sut: MoviesScreenViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fetchPopularMoviesUseCase = FetchPopularMoviesUseCase(repository)
        fetchLocalMoviesUseCase = FetchLocalMoviesUseCase(repository)
        sut = MoviesScreenViewModel(fetchPopularMoviesUseCase, fetchLocalMoviesUseCase, context)
    }

    @After
    fun tearDown() {
        // sut is constructed directly (no ViewModelStore), so nothing else cancels
        // viewModelScope between tests; without this, a coroutine left in flight by one
        // test resumes after the next test's CoroutinesTestRule resets Dispatchers.Main.
        sut.viewModelScope.cancel()
    }

    // MoviesRepositoryImpl's Flows run on Dispatchers.IO (a real dispatcher, not virtual
    // test time), so assertions poll with a bounded timeout instead of asserting immediately.
    private suspend fun awaitState(predicate: () -> Boolean) {
        withTimeout(2000) {
            while (!predicate()) {
                delay(10)
            }
        }
    }

    @Test
    fun getMoviesListWithNotEmptyList() = runBlocking {
        whenever(repository.getPopularMovies()).thenReturn(flow {
            emit(movieModelList)
        })

        Assert.assertTrue(
            (sut.moviesScreenState.value is MoviesScreenState.ShowLoading)
                    && (sut.moviesScreenState.value as MoviesScreenState.ShowLoading).isLoading
        )

        sut.getMoviesList()
        awaitState { sut.moviesScreenState.value is MoviesScreenState.ShowMovies }

        Assert.assertTrue(
            (sut.moviesScreenState.value as MoviesScreenState.ShowMovies).movieList.isNotEmpty()
        )
    }

    @Test
    fun getMoviesListWithEmptyList() = runBlocking {
        whenever(repository.getPopularMovies()).thenReturn(flow {
            emit(listOf())
        })

        Assert.assertTrue(
            (sut.moviesScreenState.value is MoviesScreenState.ShowLoading)
                    && (sut.moviesScreenState.value as MoviesScreenState.ShowLoading).isLoading
        )

        sut.getMoviesList()
        // An empty successful fetch should never transition away from ShowLoading; give the
        // real Dispatchers.IO collection a window to run before asserting nothing changed.
        delay(300)

        Assert.assertTrue(
            (sut.moviesScreenState.value is MoviesScreenState.ShowLoading)
                    && (sut.moviesScreenState.value as MoviesScreenState.ShowLoading).isLoading
        )
    }

    @Test
    fun getLocalMoviesList() = runBlocking {
        whenever(repository.getLocalMovies()).thenReturn(flow {
            emit(movieModelList)
        })

        Assert.assertTrue(
            (sut.moviesScreenState.value is MoviesScreenState.ShowLoading)
                    && (sut.moviesScreenState.value as MoviesScreenState.ShowLoading).isLoading
        )

        sut.getLocalMovies()
        awaitState { sut.moviesScreenState.value is MoviesScreenState.ShowMovies }

        Assert.assertTrue(
            (sut.moviesScreenState.value as MoviesScreenState.ShowMovies).movieList.isNotEmpty()
        )
    }

    @Test
    fun getMoviesListClearsPreviousErrorOnSuccess() = runBlocking {
        whenever(context.getString(R.string.offline_showing_saved_movies))
            .thenReturn("No internet connection.")
        whenever(repository.getPopularMovies()).thenReturn(flow { throw RuntimeException("offline") })
        whenever(repository.getLocalMovies()).thenReturn(flow { emit(movieModelList) })

        sut.getMoviesList()
        awaitState { sut.movieErrorState.value.isNotEmpty() }
        Assert.assertTrue(sut.movieErrorState.value.isNotEmpty())

        whenever(repository.getPopularMovies()).thenReturn(flow { emit(movieModelList) })
        sut.getMoviesList()

        // getMoviesList() clears movieErrorState synchronously before launching the fetch.
        Assert.assertTrue(sut.movieErrorState.value.isEmpty())
    }
}
