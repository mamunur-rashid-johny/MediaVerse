package com.johny.mediaverse.presentation.tv_show_details

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TvShowDetailsRoute(navController: NavController) {
    val viewModel: TvShowDetailsViewModel = koinViewModel()
    TvDetailsScreen(
        tvShowId = viewModel.route.tvShowId
    )
}