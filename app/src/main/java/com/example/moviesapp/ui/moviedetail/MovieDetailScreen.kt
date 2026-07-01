package com.example.moviesapp.ui.moviedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.moviesapp.R
import com.example.moviesapp.ui.models.MovieUIModel
import com.example.moviesapp.ui.moviesscreen.widgets.ShowLoader

@Composable
fun MovieDetailScreen(
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val movie = viewModel.movie.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = movie?.title.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (movie == null) {
                ShowLoader(true)
            } else {
                MovieDetailContent(movie)
            }
        }
    }
}

@Composable
private fun MovieDetailContent(movie: MovieUIModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = movie.backdropUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = movie.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = stringResource(R.string.detail_rating, movie.voteAverage.toString()))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.detail_release_date, movie.releaseDate))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = stringResource(R.string.detail_popularity, movie.popularity.toString()))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = movie.overview, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
