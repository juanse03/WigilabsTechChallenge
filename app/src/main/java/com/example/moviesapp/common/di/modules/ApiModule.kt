package com.example.moviesapp.common.di.modules

import android.content.Context
import androidx.room.Room
import com.example.moviesapp.common.constants.BASE_URL
import com.example.moviesapp.data.datasource.api.MoviesApi
import com.example.moviesapp.data.datasource.localsource.MovieDao
import com.example.moviesapp.data.datasource.localsource.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private fun provideOkHttp() : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1,TimeUnit.SECONDS)
            .writeTimeout(1,TimeUnit.SECONDS)
            .readTimeout(1,TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, MovieDatabase::class.java, "movie")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviesDAO(appDatabase: MovieDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}
