package com.example.moviesapp.ui.moviesscreen.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.moviesapp.R
import com.example.moviesapp.ui.moviesscreen.MoviesScreenViewModel

@Composable
fun RequestInternetConnectionScreen(
    errorState: State<String>,
    viewModel: MoviesScreenViewModel
) {
    AnimatedVisibility(visible = errorState.value == stringResource(R.string.local_database_without_data)) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.local_database_empty_title))
            Text(text = stringResource(R.string.local_database_empty_message))
            Button(onClick = { viewModel.refresh() }) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}
