package com.example.moviesapp.ui.moviesscreen.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShowError(error: String) {
    Snackbar (modifier = Modifier.fillMaxWidth().height(50.dp)){
        Text(text = error)
    }
}
