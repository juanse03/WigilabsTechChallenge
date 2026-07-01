package com.example.moviesapp.ui.moviesscreen

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moviesapp.ui.moviesscreen.widgets.HomeScreen
import com.example.moviesapp.ui.moviesscreen.widgets.ShowLoader
import com.example.moviesapp.ui.state.MoviesScreenState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesScreen(
    onItemClick: (Int) -> Unit,
    viewModel: MoviesScreenViewModel = hiltViewModel<MoviesScreenViewModel>()
) {
    val refreshing = remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(
        refreshing.value, viewModel::refresh
    )
    val moviesState = viewModel.moviesScreenState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMoviesList()
    }

    when (moviesState.value) {
        is MoviesScreenState.ShowMovies -> {
            refreshing.value = false
            HomeScreen(
                Modifier.pullRefresh(pullRefreshState),
                (moviesState.value as MoviesScreenState.ShowMovies).movieList,
                viewModel,
                onItemClick
            )
        }

        is MoviesScreenState.ShowLoading -> {
            refreshing.value = true
            ShowLoader(
                (moviesState.value as MoviesScreenState.ShowLoading).isLoading
            )
        }
    }
}
