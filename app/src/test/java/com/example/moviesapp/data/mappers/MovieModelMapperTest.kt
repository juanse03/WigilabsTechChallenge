package com.example.moviesapp.data.mappers

import com.example.moviesapp.common.constants.IMAGE_BASE_URL
import com.example.moviesapp.data.models.MovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

class MovieModelMapperTest {

    @Test
    fun mapsFullDtoWithPrefixedImageUrls() {
        val dto = MovieDto(
            id = 1,
            title = "Title",
            overview = "Overview",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2024-01-01",
            voteAverage = 7.5,
            popularity = 100.0
        )

        val model = dto.toMovieModel()

        assertEquals(1, model.movieId)
        assertEquals("Title", model.title)
        assertEquals("Overview", model.overview)
        assertEquals("$IMAGE_BASE_URL/poster.jpg", model.posterUrl)
        assertEquals("$IMAGE_BASE_URL/backdrop.jpg", model.backdropUrl)
        assertEquals("2024-01-01", model.releaseDate)
        assertEquals(7.5, model.voteAverage, 0.0)
        assertEquals(100.0, model.popularity, 0.0)
    }

    @Test
    fun fallsBackToPosterPathWhenBackdropPathIsNull() {
        val dto = MovieDto(
            id = 2,
            title = "Title",
            overview = "Overview",
            posterPath = "/poster.jpg",
            backdropPath = null,
            releaseDate = "2024-01-01",
            voteAverage = 5.0,
            popularity = 10.0
        )

        val model = dto.toMovieModel()

        assertEquals("$IMAGE_BASE_URL/poster.jpg", model.backdropUrl)
    }

    @Test
    fun blanksImageUrlsAndReleaseDateWhenNull() {
        val dto = MovieDto(
            id = 3,
            title = "Title",
            overview = "Overview",
            posterPath = null,
            backdropPath = null,
            releaseDate = null,
            voteAverage = 0.0,
            popularity = 0.0
        )

        val model = dto.toMovieModel()

        assertEquals("", model.posterUrl)
        assertEquals("", model.backdropUrl)
        assertEquals("", model.releaseDate)
    }
}
