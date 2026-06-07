package com.johny.mediaverse.presentation.trending

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination.*
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import com.johny.mediaverse.utils.MediaTypeEnum
import com.johny.mediaverse.utils.checkInternet
import com.johny.mediaverse.utils.openConnectivitySettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Composable
internal fun TrendingRoute(navController: NavController) {
    val viewModel: TrendingViewModel = koinViewModel()
    val trendingPaged = viewModel.trends.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ObserveAsEvent(viewModel.effect) { effect ->
        when (effect) {
            TrendingEffect.RetryPagination -> {
                if (context.checkInternet()) {
                    trendingPaged.retry()
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

            is TrendingEffect.NavigateToDetails -> {
                val destination = when (effect.mediaTypeEnum) {
                    MediaTypeEnum.MOVIE -> {
                        MovieDetailRoute(effect.id)
                    }

                    MediaTypeEnum.TV -> {
                        TvShowDetailRoute(effect.id)
                    }

                    else -> {
                        null
                    }
                }

                destination?.let {
                    navController.navigate(destination)
                }
            }
        }
    }

    TrendingScreen(
        trending = trendingPaged,
        onIntent = viewModel::onIntent
    )
}