package com.example.moviesapp.data.datasource.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.domain.entities.MovieModel

@Database(entities = [MovieModel::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
