package com.johny.mediaverse.presentation.bookmark

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
import com.johny.mediaverse.presentation.podcast.components.ErrorRow
import com.johny.mediaverse.presentation.podcast.components.FullScreenError
import com.johny.mediaverse.presentation.podcast.components.LoadingRow
import com.johny.mediaverse.presentation.podcast.components.PodcastItem
import com.johny.mediaverse.presentation.podcast.components.PodcastItemShimmer

@Composable
fun BookmarkScreen(
    podcastEntity: LazyPagingItems<PodcastEntity>,
    onIntent:(BookmarkIntent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(12.dp)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        if (podcastEntity.loadState.refresh is LoadState.Loading) {
            items(10) {
                PodcastItemShimmer()
                HorizontalDivider()
            }
        } else {
            items(
                count = podcastEntity.itemCount,
                key = podcastEntity.itemKey { it.id }
            ) { index ->
                val podcast = podcastEntity[index]
                podcast?.let {
                    PodcastItem(
                        podcastUi = it.toPodcastUiModel(),
                        onItemClick = { p -> onIntent(BookmarkIntent.OnNavigateToDetails(p)) },
                        onAddBookmark = {},
                        onRemoveBookmark = {id -> onIntent(BookmarkIntent.OnRemoveBookmark(id)) }
                    )
                    HorizontalDivider()
                }
            }
        }

        podcastEntity.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item { LoadingRow() }
                }
                loadState.refresh is LoadState.Error -> {
                    item { FullScreenError(onClickRetry = { retry() }) }
                }
                loadState.append is LoadState.Error -> {
                    item { ErrorRow(onClickRetry = { retry() }) }
                }
            }
        }
    }
}