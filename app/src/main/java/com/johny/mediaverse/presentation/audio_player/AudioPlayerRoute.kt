package com.johny.mediaverse.presentation.audio_player

import android.content.Intent
import android.os.Build
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.service.MediaVerseService
import org.koin.androidx.compose.koinViewModel

@OptIn(UnstableApi::class)
@Composable
internal fun AudioPlayerRoute(navController: NavController) {
    val viewModel: AudioPlayerViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is AudioPlayerEffect.OnBack -> navController.popBackStack()
            AudioPlayerEffect.StartService -> {
                val intent = Intent(context, MediaVerseService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(intent)
                } else {
                    context.startService(intent)
                }
            }
        }
    }

    AudioPlayerScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}