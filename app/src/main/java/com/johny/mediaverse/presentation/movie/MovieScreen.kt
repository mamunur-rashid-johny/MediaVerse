package com.johny.mediaverse.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.johny.mediaverse.presentation.ui.theme.AppTypography

@Composable
fun MovieScreen(
    onIntent:(MovieIntent)->Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Movie Screen",
            style = AppTypography.bodyLarge
        )
        Button(onClick = {
            onIntent(MovieIntent.LogoutIntent)
        }) {
            Text(text = "Logout", style = AppTypography.labelLarge)
        }
    }
}