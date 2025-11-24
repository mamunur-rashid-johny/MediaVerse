package com.johny.mediaverse.presentation.audio_player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AudioPlayerRoute(navController: NavController) {
    val viewModel: AudioPlayerViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is AudioPlayerEffect.OnBack -> navController.popBackStack()
        }
    }

    AudioPlayerScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}