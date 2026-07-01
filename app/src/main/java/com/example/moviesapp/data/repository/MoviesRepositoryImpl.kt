package com.example.moviesapp.data.repository

import com.example.moviesapp.data.datasource.api.MoviesApi
import com.example.moviesapp.data.datasource.localsource.MovieDao
import com.example.moviesapp.data.mappers.toMovieModel
import com.example.moviesapp.domain.entities.MovieModel
import com.example.moviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val service: MoviesApi,
    private val dao: MovieDao
) : MoviesRepository {

    override suspend fun getPopularMovies(): Flow<List<MovieModel>> {
        return flow {
            val response = service.getPopularMovies().results.map { it.toMovieModel() }
            dao.insertAll(response)
            emit(response)
        }
    }

    override suspend fun getLocalMovies(): Flow<List<MovieModel>> {
        return flow {
            emit(dao.getAllMovies())
        }
    }

    override suspend fun getMovieById(movieId: Int): Flow<MovieModel?> {
        return flow {
            emit(dao.getMovieById(movieId))
        }
    }
}
