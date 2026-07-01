package com.example.moviesapp.domain.usecases

import com.example.moviesapp.domain.repository.MoviesRepository
import javax.inject.Inject

class FetchLocalMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke() = repository.getLocalMovies()
}
