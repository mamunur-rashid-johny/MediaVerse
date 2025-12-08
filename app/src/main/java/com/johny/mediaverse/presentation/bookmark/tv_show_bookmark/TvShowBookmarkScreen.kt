package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

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
import com.johny.mediaverse.R
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import com.johny.mediaverse.data.mapper.toTvShowUIModel
import com.johny.mediaverse.presentation.bookmark.components.BookmarkEmptyScreen
import com.johny.mediaverse.presentation.movie.components.MovieGridItemShimmer
import com.johny.mediaverse.presentation.podcast.components.FullScreenError
import com.johny.mediaverse.presentation.tv_show.components.TvShowErrorRow
import com.johny.mediaverse.presentation.tv_show.components.TvShowItem
import com.johny.mediaverse.presentation.tv_show.components.TvShowLoadingRow


@Composable
fun TvShowBookmarkScreen(
    modifier: Modifier = Modifier,
    tvShows: LazyPagingItems<TvShowEntity>,
    onIntent: (TvShowBookmarkIntent) -> Unit
) {
    val isInitialLoading = tvShows.loadState.refresh is LoadState.Loading && tvShows.itemCount == 0
    val isListEmpty = tvShows.loadState.refresh is LoadState.NotLoading && tvShows.itemCount == 0


    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(PaddingValues(12.dp)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        columns = GridCells.Fixed(2)
    ) {
        when{
            isInitialLoading ->{
                items(10) {
                    MovieGridItemShimmer()
                }
            }
            isListEmpty -> {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    BookmarkEmptyScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = "No Tv Shows Bookmarked Yet!",
                        animation = R.raw.tv_show
                    )
                }
            }
            else ->{
                items(
                    count = tvShows.itemCount,
                    key = tvShows.itemKey { it.toTvShowUIModel().tvShow.id }
                ) { index ->
                    val tvShow = tvShows[index]
                    tvShow?.let {
                        TvShowItem(
                            tvShowUi = it.toTvShowUIModel(),
                            onItemClick = { p -> onIntent(TvShowBookmarkIntent.OnTvShowBookmarkClickIntent(p.id))  },
                            onAddBookmark = {   },
                            onRemoveBookmark = { id -> onIntent(TvShowBookmarkIntent.OnTvShowBookRemoveIntent(id)) }
                        )
                    }
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
                    if (tvShows.itemCount == 0){
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            FullScreenError(onClickRetry = { retry() })
                        }
                    }
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

