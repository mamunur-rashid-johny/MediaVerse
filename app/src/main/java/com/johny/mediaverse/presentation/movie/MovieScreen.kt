package com.johny.mediaverse.presentation.movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.presentation.movie.components.ErrorRow
import com.johny.mediaverse.presentation.movie.components.LoadingRow
import com.johny.mediaverse.presentation.movie.components.MovieGridItem
import com.johny.mediaverse.presentation.movie.components.MovieGridItemShimmer
import com.johny.mediaverse.presentation.movie.model.MovieModelUi


@Composable
fun MovieScreen(
    movies: LazyPagingItems<MovieModelUi>,
    onIntent:(MovieIntent)->Unit
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
        if (movies.loadState.refresh is LoadState.Loading) {
            items(10) {
                MovieGridItemShimmer()
                HorizontalDivider()
            }
        } else {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.movie.id }
            ) { index ->
                val podcast = movies[index]
                podcast?.let {
                    MovieGridItem(
                        movieUi = it,
                        onItemClick = { p -> onIntent(MovieIntent.OnMovieDetailsNavigateIntent(p.id))  },
                        onAddBookmark = {p -> onIntent(MovieIntent.SaveBookmarkIntent(p)) },
                        onRemoveBookmark = {id -> onIntent(MovieIntent.RemoveBookmarkIntent(id))  }
                    )
                    HorizontalDivider()
                }
            }
        }

        movies.apply {
            when {
                loadState.append is LoadState.Loading -> {
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) { LoadingRow() }
                }
                loadState.refresh is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    onIntent(MovieIntent.ShowErrorIntent(error.error.message?:"Unknown Error","Retry"))
                }
                loadState.append is LoadState.Error -> {
                    val error = loadState.refresh as LoadState.Error
                    item(
                        span = { GridItemSpan(maxLineSpan) }
                    ) { ErrorRow(
                        message = error.error.message,
                        onClickRetry = { retry() }) }
                }
            }
        }
    }
}