package com.johny.mediaverse.presentation.movie_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.johny.mediaverse.presentation.ui.theme.AppTypography

@Composable
fun MovieDetailsScreen(modifier: Modifier = Modifier,movieId: Int) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Movie Details Screen \n $movieId",
            style = AppTypography.bodyLarge
        )
    }
}