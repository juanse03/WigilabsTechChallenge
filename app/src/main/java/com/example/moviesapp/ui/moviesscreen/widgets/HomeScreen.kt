package com.example.moviesapp.ui.moviesscreen.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviesapp.R
import com.example.moviesapp.ui.models.MovieUIModel
import com.example.moviesapp.ui.moviesscreen.MoviesScreenViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    movieList: List<MovieUIModel>,
    viewModel: MoviesScreenViewModel,
    onItemClick: (Int) -> Unit
) {

    val errorState = viewModel.movieErrorState.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    LaunchedEffect(errorState) {
        if (errorState.value.isNotEmpty()) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = errorState.value,
            )
        }
    }

    Scaffold(
        modifier = modifier.background(Color.White),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.popular_movies_title)) })
        }
    ) {
        Column(modifier = Modifier.padding(it).padding(top = 12.dp)) {
            RequestInternetConnectionScreen(errorState, viewModel)
            ShowMovieList(modifier, movieList, onItemClick)
        }
    }

}
