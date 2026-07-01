package com.example.moviesapp.data.mappers

import com.example.moviesapp.common.constants.IMAGE_BASE_URL
import com.example.moviesapp.data.models.MovieDto
import com.example.moviesapp.domain.entities.MovieModel

fun MovieDto.toMovieModel() = MovieModel(
    movieId = this.id,
    title = this.title,
    overview = this.overview,
    posterUrl = this.posterPath?.let { IMAGE_BASE_URL + it }.orEmpty(),
    backdropUrl = (this.backdropPath ?: this.posterPath)?.let { IMAGE_BASE_URL + it }.orEmpty(),
    releaseDate = this.releaseDate.orEmpty(),
    voteAverage = this.voteAverage,
    popularity = this.popularity,
)
