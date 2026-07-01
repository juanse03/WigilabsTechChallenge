package com.example.moviesapp.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.usecases.GetMovieByIdUseCase
import com.example.moviesapp.ui.mappers.toMovieUIModel
import com.example.moviesapp.ui.models.MovieUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _movie = MutableStateFlow<MovieUIModel?>(null)
    val movie get() = _movie

    init {
        viewModelScope.launch {
            getMovieByIdUseCase.invoke(movieId)
                .map { it?.toMovieUIModel() }
                .flowOn(Dispatchers.IO)
                .collect { _movie.value = it }
        }
    }
}
