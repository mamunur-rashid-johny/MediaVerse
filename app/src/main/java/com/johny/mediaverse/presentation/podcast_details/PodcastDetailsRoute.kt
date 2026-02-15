package com.johny.mediaverse.presentation.podcast_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.navigation.Destination.AudioPlayerRoute
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PodcastDetailsRoute(navController: NavController) {
    val viewModel: PodcastDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is PodcastDetailsEffect.NavigateToAudioPlayer -> {
                navController.navigate(
                    AudioPlayerRoute(effect.episodeModel)
                )
            }

            PodcastDetailsEffect.OnBackPressed -> {
                navController.navigateUp()
            }

            is PodcastDetailsEffect.NavigateToWebviewEffect -> {
                navController.navigate(
                    Destination.WebViewRoute(
                        url = effect.url ?: "",
                        title = effect.title
                    )
                )
            }
        }
    }

    PodcastDetailsScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}