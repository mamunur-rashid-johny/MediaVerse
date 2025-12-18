package com.johny.mediaverse.presentation.podcast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination.PodcastDetailRoute
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel
import com.johny.mediaverse.utils.checkInternet
import com.johny.mediaverse.utils.openConnectivitySettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodcastRoute(navController: NavController) {
    val viewModel: PodcastViewModel = koinViewModel()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val podcast: LazyPagingItems<PodcastUIModel> = viewModel.podcast.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                is PodcastEffect.NavigateToDetail -> {
                    navController.navigate(PodcastDetailRoute(event.podcast))
                }

                PodcastEffect.RetryPaginationEffect -> {
                    if (context.checkInternet()) {
                        podcast.retry()
                    } else {
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
    }

    PodcastScreen(
        podcasts = podcast,
        onIntent = viewModel::onIntent
    )
}