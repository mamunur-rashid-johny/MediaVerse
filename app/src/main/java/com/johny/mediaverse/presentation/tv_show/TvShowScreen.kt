package com.johny.mediaverse.presentation.tv_show

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.core.navigation.LocalScaffoldPadding
import com.johny.mediaverse.core.presentation.components.AnimatedGradientText
import com.johny.mediaverse.core.presentation.components.EmptyOrErrorScreen
import com.johny.mediaverse.core.presentation.components.ErrorRow
import com.johny.mediaverse.core.presentation.components.LoadingRow
import com.johny.mediaverse.presentation.tv_show.components.TvShowItemGrid
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvShowScreen(
    tvShows: LazyPagingItems<TvShowUiModel>,
    onIntent: (TvShowIntent) -> Unit
) {
    when {
        tvShows.loadState.refresh is LoadState.Error && tvShows.itemCount == 0 -> {
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
        }

        tvShows.loadState.refresh is LoadState.Loading && tvShows.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        else -> {
            val scaffoldPadding = LocalScaffoldPadding.current
            val layoutDirection = LocalLayoutDirection.current
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = scaffoldPadding.calculateStartPadding(layoutDirection) + 20.dp,
                    end = scaffoldPadding.calculateEndPadding(layoutDirection) + 20.dp,
                    top = scaffoldPadding.calculateTopPadding() + 20.dp,
                    bottom = scaffoldPadding.calculateBottomPadding() + 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                columns = GridCells.Fixed(2)
            ) {

                item(
                    span = { GridItemSpan(maxLineSpan) }
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AnimatedGradientText(text = "Discover TV Shows")
                    }
                }

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
}