package com.johny.mediaverse.presentation.tv_show_season_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TvShowSeasonDetailsRoute(navController: NavController) {
    val viewModel: TvShowSeasonDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is TvShowSeasonSideEffect.OnShowErrorSideEffect -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = effect.error.toString(context)
                        )
                    )
                }
            }
        }
    }
    TvShowSeasonDetailsScreen(state)
}