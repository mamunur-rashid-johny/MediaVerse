package com.johny.mediaverse.presentation.bookmark

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookmarkRoute(navController: NavController) {

    val viewModel: BookmarkViewModel = koinViewModel()
    val podcastEntity: LazyPagingItems<PodcastEntity> =
        viewModel.savedPodcast.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) {effect ->
        when(effect){
            is BookmarkEffect.NavigateToDetails -> {
                navController.navigate(Destination.PodcastDetailRoute(effect.podcast))
            }
        }
    }

    BookmarkScreen(
        podcastEntity = podcastEntity,
        onIntent = viewModel::onIntent
    )
}