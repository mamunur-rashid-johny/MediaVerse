package com.johny.mediaverse.presentation.movie

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
import com.johny.mediaverse.presentation.movie.components.MovieGridItem
import com.johny.mediaverse.presentation.movie.components.MovieGridItemShimmer
import com.johny.mediaverse.presentation.movie.model.MovieModelUi


@Composable
fun MovieScreen(
    movies: LazyPagingItems<MovieModelUi>,
    onIntent: (MovieIntent) -> Unit
) {

    if (movies.loadState.refresh is LoadState.Error && movies.itemCount == 0) {
        val error = movies.loadState.refresh as LoadState.Error
        EmptyOrErrorScreen(
            title = "Error!",
            info = error.error.message
                ?: "Unknown error occurred, try again!",
            primaryLabel = "Retry",
            modifier = Modifier.fillMaxSize()
        ) {
            onIntent(MovieIntent.OnRetryPagination)
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
            when {
                movies.loadState.refresh is LoadState.Loading && movies.itemCount == 0 -> {
                    items(10) {
                        MovieGridItemShimmer()
                    }
                }

                else -> {
                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { it.movie.id }
                    ) { index ->
                        val podcast = movies[index]
                        podcast?.let {
                            MovieGridItem(
                                movieUi = it,
                                onIntent = onIntent
                            )
                        }
                    }
                }
            }

            movies.apply {
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
                            ErrorRow(
                                message = error.error.message
                            ) {
                                onIntent(MovieIntent.OnRetryPagination)
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