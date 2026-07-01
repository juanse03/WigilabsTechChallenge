package com.example.moviesapp

import com.example.moviesapp.common.constants.IMAGE_BASE_URL
import com.example.moviesapp.data.models.MovieDto
import com.example.moviesapp.data.models.MoviesApiResponse
import com.example.moviesapp.domain.entities.MovieModel

const val MOVIE_ID = 123
const val MOVIE_TITLE = "Movie Title"
const val MOVIE_OVERVIEW = "Movie overview description."
const val MOVIE_POSTER_PATH = "/poster.jpg"
const val MOVIE_BACKDROP_PATH = "/backdrop.jpg"
const val MOVIE_RELEASE_DATE = "2024-06-01"
const val MOVIE_VOTE_AVERAGE = 7.8
const val MOVIE_POPULARITY = 123.45

val movieModelList = listOf(
    MovieModel(
        MOVIE_ID,
        MOVIE_TITLE,
        MOVIE_OVERVIEW,
        IMAGE_BASE_URL + MOVIE_POSTER_PATH,
        IMAGE_BASE_URL + MOVIE_BACKDROP_PATH,
        MOVIE_RELEASE_DATE,
        MOVIE_VOTE_AVERAGE,
        MOVIE_POPULARITY
    )
)

fun generateMockMoviesApiResponse(): MoviesApiResponse {
    return MoviesApiResponse(
        page = 1,
        results = listOf(
            MovieDto(
                id = MOVIE_ID,
                title = MOVIE_TITLE,
                overview = MOVIE_OVERVIEW,
                posterPath = MOVIE_POSTER_PATH,
                backdropPath = MOVIE_BACKDROP_PATH,
                releaseDate = MOVIE_RELEASE_DATE,
                voteAverage = MOVIE_VOTE_AVERAGE,
                popularity = MOVIE_POPULARITY
            )
        ),
        totalPages = 1,
        totalResults = 1
    )
}
