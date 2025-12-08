package com.johny.mediaverse.presentation.tv_show

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TvShowRoute(navController: NavController) {
    val viewModel: TvShowViewModel = koinViewModel()
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is TvShowSideEffect.NavigateToDetailsEffect -> {
                navController.navigate(Destination.TvShowDetailRoute(effect.tvShowId))
            }

            is TvShowSideEffect.ShowErrorEffect -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = effect.message,
                            action = SnackbarAction(
                                name = effect.actionLabel,
                                action = {
                                    tvShows.retry()
                                }
                            )
                        )
                    )
                }
            }
        }
    }
    TvShowScreen(
        tvShows = tvShows,
        onIntent = viewModel::onIntent
    )
}