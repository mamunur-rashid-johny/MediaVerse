package com.johny.mediaverse.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent

@Composable
fun BookmarkRoute(navController: NavController) {
    val viewModel: BookmarkViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is BookmarkEffect.NavigateToMovieDetails -> {
                navController.navigate(Destination.MovieDetailRoute(effect.movieId))
            }

            is BookmarkEffect.NavigateToPodcastDetails -> {
                navController.navigate(Destination.PodcastDetailRoute(effect.podcast))
            }

            is BookmarkEffect.NavigateToTvShowDetails -> {
                navController.navigate(Destination.TvShowDetailRoute(effect.tvShowId))
            }
        }
    }

    BookmarkScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}