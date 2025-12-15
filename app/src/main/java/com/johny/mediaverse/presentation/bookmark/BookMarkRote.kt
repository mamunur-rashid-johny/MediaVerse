package com.johny.mediaverse.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination.MovieDetailRoute
import com.johny.mediaverse.core.navigation.Destination.MovieRoute
import com.johny.mediaverse.core.navigation.Destination.PodcastDetailRoute
import com.johny.mediaverse.core.navigation.Destination.PodcastRoute
import com.johny.mediaverse.core.navigation.Destination.TvShowDetailRoute
import com.johny.mediaverse.core.navigation.Destination.TvShowRoute
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent

@Composable
fun BookmarkRoute(navController: NavController) {
    val viewModel: BookmarkViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is BookmarkEffect.NavigateToMovieDetails -> {
                navController.navigate(MovieDetailRoute(effect.movieId))
            }

            is BookmarkEffect.NavigateToPodcastDetails -> {
                navController.navigate(PodcastDetailRoute(effect.podcast))
            }

            is BookmarkEffect.NavigateToTvShowDetails -> {
                navController.navigate(TvShowDetailRoute(effect.tvShowId))
            }

            BookmarkEffect.NavigateToMovie -> {
                navController.navigate(MovieRoute)
            }

            BookmarkEffect.NavigateToPodcast -> {
                navController.navigate(PodcastRoute)
            }

            BookmarkEffect.NavigateToTvShow -> {
                navController.navigate(TvShowRoute)
            }
        }
    }

    BookmarkScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}