package com.example.moviesapp.data.datasource.localsource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapp.domain.entities.MovieModel

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie ORDER BY sortOrder ASC")
    fun getAllMovies(): List<MovieModel>

    @Query("SELECT * FROM movie WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): MovieModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieListModel: List<MovieModel>)
}
