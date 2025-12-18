package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.core.presentation.components.EmptyOrErrorScreen
import com.johny.mediaverse.core.presentation.components.ErrorRow
import com.johny.mediaverse.core.presentation.components.LoadingRow
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.mapper.toPodcastUiModel

@Composable
fun PodcastBookmarkScreen(
    modifier: Modifier,
    podcast: LazyPagingItems<PodcastEntity>,
    onIntent: (PodcastBookmarkIntent) -> Unit
) {

    val isListEmpty = podcast.loadState.refresh is LoadState.Error && podcast.itemCount == 0

    if (isListEmpty) {
        EmptyOrErrorScreen(
            title = "No Data Found",
            info = "You haven’t bookmarked any movies yet. Start exploring and save your favorites to see them",
            primaryLabel = "Add Podcast to Bookmark",
            action = {
                onIntent(PodcastBookmarkIntent.OnNavigateToPodcast)
            }
        )
    } else {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(PaddingValues(12.dp)),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(
                count = podcast.itemCount,
                key = podcast.itemKey { it.id }
            ) { index ->
                val podcastItem = podcast[index]
                podcastItem?.let {
                    PodcastBookmarkItem(
                        podcastUi = it.toPodcastUiModel(),
                        onIntent = onIntent
                    )
                    if (index < podcast.itemCount - 1){
                        HorizontalDivider()
                    }
                }
            }

            podcast.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            LoadingRow()
                        }
                    }

                    is LoadState.Error -> {
                        item {
                            val error = podcast.loadState.append as LoadState.Error
                            ErrorRow(
                                message = error.error.message ?: "Unknown error"
                            ) {
                                retry()
                            }
                        }
                    }

                    else -> {
                        //nothing to do
                    }
                }
            }
        }
    }
}