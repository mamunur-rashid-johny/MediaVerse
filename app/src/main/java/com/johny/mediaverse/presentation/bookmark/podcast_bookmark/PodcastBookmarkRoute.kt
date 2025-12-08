package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.domain.model.podcast.Podcast
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodcastBookmarkRoute(modifier: Modifier = Modifier,onItemClick:(Podcast)->Unit) {
    val viewModel: PodcastBookmarkViewModel = koinViewModel()
    val savedPodcast = viewModel.savedPodcast.collectAsLazyPagingItems()

    ObserveAsEvent(
        events = viewModel.effect
    ) {
        when(it){
            is PodcastBookmarkEffect.OnNavigateToPodcastDetailEffect -> {
                onItemClick(it.podcast)
            }
        }
    }

    PodcastBookmarkScreen(
        modifier = modifier,
        podcastEntity = savedPodcast,
        onIntent = viewModel::onIntent
    )
}

