package com.example.moviesapp.data.datasource.api

import com.example.moviesapp.common.constants.PUBLIC_KEY
import com.example.moviesapp.data.models.MoviesApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = PUBLIC_KEY,
        @Query("language") language: String = "en-US"
    ): MoviesApiResponse
}
