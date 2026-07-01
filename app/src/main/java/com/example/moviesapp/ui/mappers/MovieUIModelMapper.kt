package com.example.moviesapp.ui.mappers

import com.example.moviesapp.domain.entities.MovieModel
import com.example.moviesapp.ui.models.MovieUIModel

fun MovieModel.toMovieUIModel() = MovieUIModel(
    id = this.movieId,
    title = this.title,
    overview = this.overview,
    posterUrl = this.posterUrl,
    backdropUrl = this.backdropUrl,
    releaseDate = this.releaseDate,
    voteAverage = this.voteAverage,
    popularity = this.popularity,
)
