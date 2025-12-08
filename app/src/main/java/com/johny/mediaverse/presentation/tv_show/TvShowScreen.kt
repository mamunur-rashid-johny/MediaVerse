package com.johny.mediaverse.presentation.tv_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.presentation.movie.components.MovieGridItemShimmer
import com.johny.mediaverse.presentation.tv_show.components.TvShowErrorRow
import com.johny.mediaverse.presentation.tv_show.components.TvShowItem
import com.johny.mediaverse.presentation.tv_show.components.TvShowLoadingRow
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel

@Composable
fun TvShowScreen(
    tvShows: LazyPagingItems<TvShowUiModel>,
    onIntent: (TvShowIntent) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(12.dp)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2)
    ) {
        if (tvShows.loadState.refresh is LoadState.Loading) {
            items(10) {
                MovieGridItemShimmer()
            }
        } else {
            items(
                count = tvShows.itemCount,
                key = tvShows.itemKey { it.tvShow.id }
            ) { index ->
                val tvShow = tvShows[index]
                tvShow?.let {
                    TvShowItem(
                        tvShowUi = it,
                        onItemClick = { p -> onIntent(TvShowIntent.NavigateToDetailsIntent(p.id)) },
                        onAddBookmark = { p -> onIntent(TvShowIntent.SaveBookmarkIntent(p)) },
                        onRemoveBookmark = { id -> onIntent(TvShowIntent.RemoveBookmarkIntent(id)) }
                    )
                }
            }
        }

        tvShows.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) { TvShowLoadingRow() }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    onIntent(TvShowIntent.ShowErrorIntent(error.error.message ?: "Unknown Error", "Retry"))
                }

                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        TvShowErrorRow(message = error.error.message, onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}