package com.johny.mediaverse.presentation.tv_show

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination.TvShowDetailRoute
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import com.johny.mediaverse.utils.checkInternet
import com.johny.mediaverse.utils.openConnectivitySettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TvShowRoute(navController: NavController) {
    val viewModel: TvShowViewModel = koinViewModel()
    val tvShows = viewModel.tvShows.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is TvShowSideEffect.NavigateToDetailsEffect -> {
                navController.navigate(TvShowDetailRoute(effect.tvShowId))
            }
            TvShowSideEffect.RetryPaginationEffect -> {
                if (context.checkInternet()){
                    tvShows.retry()
                }else{
                    scope.launch {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "No Internet Connection is Available",
                                action = SnackbarAction(
                                    name = "Settings",
                                    action = {
                                        context.openConnectivitySettings()
                                    }
                                )
                            )
                        )
                    }
                }
            }
        }
    }
    TvShowScreen(
        tvShows = tvShows,
        onIntent = viewModel::onIntent
    )
}