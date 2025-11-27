package com.johny.mediaverse.presentation.movie_details

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MovieDetailsRoute(navController: NavController) {
    val viewModel: MovieDetailsViewModel = koinViewModel()
    MovieDetailsScreen(
        movieId = viewModel.route.movieId
    )
}