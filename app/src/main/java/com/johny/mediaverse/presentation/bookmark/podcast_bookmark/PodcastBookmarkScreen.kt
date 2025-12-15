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
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.mapper.toPodcastUiModel
import com.johny.mediaverse.core.presentation.components.EmptyScreen
import com.johny.mediaverse.presentation.podcast.components.ErrorRow
import com.johny.mediaverse.presentation.podcast.components.FullScreenError
import com.johny.mediaverse.presentation.podcast.components.LoadingRow
import com.johny.mediaverse.presentation.podcast.components.PodcastItem
import com.johny.mediaverse.presentation.podcast.components.PodcastItemShimmer

@Composable
fun PodcastBookmarkScreen(
    modifier: Modifier,
    podcastEntity: LazyPagingItems<PodcastEntity>,
    onIntent: (PodcastBookmarkIntent) -> Unit
) {
    val isInitialLoading =
        podcastEntity.loadState.refresh is LoadState.Loading && podcastEntity.itemCount == 0
    val isListEmpty =
        podcastEntity.loadState.refresh is LoadState.NotLoading && podcastEntity.itemCount == 0

    if (isListEmpty) {
        EmptyScreen(
            title = "No Data Found",
            info = "You haven’t bookmarked any movies yet. Start exploring and save your favorites to see them",
            label = "Add Podcast to Bookmark",
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
            when {
                isInitialLoading -> {
                    items(10) {
                        PodcastItemShimmer()
                        HorizontalDivider()
                    }
                }

                else -> {
                    items(
                        count = podcastEntity.itemCount,
                        key = podcastEntity.itemKey { it.id }
                    ) { index ->
                        val podcast = podcastEntity[index]
                        podcast?.let {
                            PodcastItem(
                                podcastUi = it.toPodcastUiModel(),
                                onItemClick = { p ->
                                    onIntent(
                                        PodcastBookmarkIntent.OnPodcastBookmarkClickIntent(
                                            p
                                        )
                                    )
                                },
                                onAddBookmark = {},
                                onRemoveBookmark = { id ->
                                    onIntent(
                                        PodcastBookmarkIntent.OnPodcastBookRemoveIntent(
                                            id
                                        )
                                    )
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }

            podcastEntity.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingRow() }
                    }

                    loadState.refresh is LoadState.Error -> {
                        if (podcastEntity.itemCount == 0) {
                            item { FullScreenError(onClickRetry = { retry() }) }
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item { ErrorRow(onClickRetry = { retry() }) }
                    }
                }
            }
        }
    }
}