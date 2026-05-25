package com.johny.mediaverse.presentation.tv_show_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.navigation.Destination.*
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TvShowDetailsRoute(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: TvShowDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when(effect){
            is TvShowDetailsSideEffect.OnNavigateSideEffect -> {
                navController.navigate(TvShowSeasonDetailRoute(tvShowId = effect.seriesId, seasonNumber = effect.seasonNumber))
            }
            is TvShowDetailsSideEffect.ShowErrorMessage -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = effect.message.toString(context)
                        )
                    )
                }
            }

            is TvShowDetailsSideEffect.NavigateToDetailsEffect -> {
                navController.navigate(TvShowDetailRoute(effect.tvShowId))
            }
        }
    }

    TvDetailsScreen(
        state = state,
        tvShows = tvShows,
        onIntent = viewModel::onIntent
    )
}