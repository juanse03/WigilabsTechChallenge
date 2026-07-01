package com.example.moviesapp.ui.moviesscreen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moviesapp.ui.models.MovieUIModel

@Composable
fun ShowMovieList(
    modifier: Modifier,
    movieList: List<MovieUIModel>,
    onItemClick: (Int) -> Unit
) {
    LazyColumn(
        modifier.background(Color.White),
    ) {
        items(
            movieList,
            key = { movie -> movie.id }) { movie ->
            MovieItem(movie, onItemClick)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
