package com.johny.mediaverse.presentation.movie_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MovieDetailsRoute(navController: NavController) {
    val viewModel: MovieDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieDetailsScreen(
        state = state
    )
}