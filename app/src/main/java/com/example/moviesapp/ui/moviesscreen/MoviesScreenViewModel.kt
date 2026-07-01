package com.example.moviesapp.ui.moviesscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.R
import com.example.moviesapp.domain.usecases.FetchLocalMoviesUseCase
import com.example.moviesapp.domain.usecases.FetchPopularMoviesUseCase
import com.example.moviesapp.ui.mappers.toMovieUIModel
import com.example.moviesapp.ui.state.MoviesScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val fetchPopularMoviesUseCase: FetchPopularMoviesUseCase,
    private val fetchLocalMoviesUseCase: FetchLocalMoviesUseCase,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _moviesScreenState =
        MutableStateFlow<MoviesScreenState>(MoviesScreenState.ShowLoading(true))
    val moviesScreenState get() = _moviesScreenState

    private val _movieErrorState = MutableStateFlow("")
    val movieErrorState get() = _movieErrorState

    fun getMoviesList() {
        _movieErrorState.value = ""
        viewModelScope.launch {
            fetchPopularMoviesUseCase.invoke()
                .catch {
                    _movieErrorState.value = context.getString(R.string.offline_showing_saved_movies)
                    getLocalMovies()
                }
                .map { it.map { movieModel -> movieModel.toMovieUIModel() } }
                .flowOn(Dispatchers.IO)
                .collect { movieList ->
                    if (movieList.isNotEmpty()) {
                        _moviesScreenState.value = (MoviesScreenState.ShowMovies(movieList))
                    }
                }
        }
    }

    fun getLocalMovies() {
        viewModelScope.launch {
            fetchLocalMoviesUseCase.invoke()
                .catch {
                    _movieErrorState.value = it.message.orEmpty()
                }
                .map { it.map { movieModel -> movieModel.toMovieUIModel() } }
                .flowOn(Dispatchers.IO)
                .collect { movieList ->
                    if (movieList.isEmpty()) {
                        _movieErrorState.value = context.getString(R.string.local_database_without_data)
                    }
                    _moviesScreenState.tryEmit(MoviesScreenState.ShowMovies(movieList))
                }
        }
    }

    fun refresh() {
        _moviesScreenState.value = MoviesScreenState.ShowLoading(true)
        getMoviesList()
    }
}
