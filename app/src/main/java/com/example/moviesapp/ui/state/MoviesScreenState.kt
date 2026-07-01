package com.example.moviesapp.ui.state

import com.example.moviesapp.ui.models.MovieUIModel

sealed class MoviesScreenState {
    class ShowLoading(val isLoading: Boolean) : MoviesScreenState()

    class ShowMovies(val movieList: List<MovieUIModel>) :
        MoviesScreenState()
}
