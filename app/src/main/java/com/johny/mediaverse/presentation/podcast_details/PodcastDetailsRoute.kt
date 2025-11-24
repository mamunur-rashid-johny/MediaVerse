package com.johny.mediaverse.presentation.podcast_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination.AudioPlayerRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PodcastDetailsRoute(navController: NavController) {
    val viewModel: PodcastDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is PodcastDetailsEffect.NavigateToAudioPlayer -> {
                    navController.navigate(
                        AudioPlayerRoute(effect.episodeModel)
                    )
                }

                PodcastDetailsEffect.OnBackPressed -> {
                    navController.navigateUp()
                }
            }
        }
    }

    PodcastDetailsScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}