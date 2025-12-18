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
import com.johny.mediaverse.core.presentation.components.EmptyOrErrorScreen
import com.johny.mediaverse.core.presentation.components.ErrorRow
import com.johny.mediaverse.core.presentation.components.LoadingRow
import com.johny.mediaverse.presentation.tv_show.components.TvShowItemGrid
import com.johny.mediaverse.presentation.tv_show.components.TvShowItemGridShimmer
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel

@Composable
fun TvShowScreen(
    tvShows: LazyPagingItems<TvShowUiModel>,
    onIntent: (TvShowIntent) -> Unit
) {

    if (tvShows.loadState.refresh is LoadState.Error && tvShows.itemCount == 0) {
        val error = tvShows.loadState.refresh as LoadState.Error
        EmptyOrErrorScreen(
            title = "Error!",
            info = error.error.message
                ?: "Unknown error occurred, try again!",
            primaryLabel = "Retry",
            modifier = Modifier.fillMaxSize()
        ) {
            onIntent(TvShowIntent.OnRetryPagination)
        }
    } else {

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(12.dp)),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2)
        ) {
            when{
                tvShows.loadState.refresh is LoadState.Loading && tvShows.itemCount == 0  ->{
                    items(10) {
                        TvShowItemGridShimmer()
                    }
                }
                else ->{
                    items(
                        count = tvShows.itemCount,
                        key = tvShows.itemKey { it.tvShow.id }
                    ) { index ->
                        val tvShow = tvShows[index]
                        tvShow?.let {
                            TvShowItemGrid(
                                tvShowUi = it,
                                onIntent = onIntent
                            )
                        }
                    }
                }
            }

            tvShows.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            LoadingRow()
                        }
                    }

                    is LoadState.Error -> {
                        val error = loadState.append as LoadState.Error
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            ErrorRow(message = error.error.message, onRetry = { retry() })
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