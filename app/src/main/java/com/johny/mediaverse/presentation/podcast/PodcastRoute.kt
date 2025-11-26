package com.johny.mediaverse.presentation.podcast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodcastRoute(navController: NavController) {
    val viewModel: PodcastViewModel = koinViewModel()
    val podcast: LazyPagingItems<PodcastUIModel> = viewModel.podcast.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { event ->
            when (event) {
                is PodcastEffect.NavigateToDetail -> {
                    navController.navigate(Destination.PodcastDetailRoute(event.podcast))
                }
            }
        }
    }

    PodcastScreen(
        podcastsUiModel = podcast,
        onIntent = viewModel::onIntent
    )
}