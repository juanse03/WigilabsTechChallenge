package com.example.moviesapp.domain.repository

import com.example.moviesapp.domain.entities.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun getPopularMovies(): Flow<List<MovieModel>>
    suspend fun getLocalMovies(): Flow<List<MovieModel>>
    suspend fun getMovieById(movieId: Int): Flow<MovieModel?>
}
