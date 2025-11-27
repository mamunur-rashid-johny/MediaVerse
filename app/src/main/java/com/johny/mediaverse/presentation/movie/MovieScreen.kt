package com.johny.mediaverse.presentation.movie

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
import com.johny.mediaverse.presentation.movie.components.ErrorRow
import com.johny.mediaverse.presentation.movie.components.FullScreenError
import com.johny.mediaverse.presentation.movie.components.LoadingRow
import com.johny.mediaverse.presentation.movie.components.MovieItem
import com.johny.mediaverse.presentation.movie.components.MovieItemShimmer
import com.johny.mediaverse.presentation.movie.model.MovieModelUi


@Composable
fun MovieScreen(
    movies: LazyPagingItems<MovieModelUi>,
    onIntent:(MovieIntent)->Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(12.dp)),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
    ) {
        if (movies.loadState.refresh is LoadState.Loading) {
            items(10) {
                MovieItemShimmer()
                HorizontalDivider()
            }
        } else {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.movie.id }
            ) { index ->
                val podcast = movies[index]
                podcast?.let {
                    MovieItem(
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