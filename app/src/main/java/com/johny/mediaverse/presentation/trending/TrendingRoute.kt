package com.johny.mediaverse.presentation.trending

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Composable
internal fun TrendingRoute(navController: NavController) {
    val viewModel: TrendingViewModel = koinViewModel()
    val trendingPaged = viewModel.trending.collectAsLazyPagingItems()
    TrendingScreen(
        trending = trendingPaged
    )
}