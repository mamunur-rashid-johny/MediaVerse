package com.johny.mediaverse.presentation.bookmark.movie_bookmark

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
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.mapper.toMovieUIModel


@Composable
fun MovieBookmarkScreen(
    modifier: Modifier,
    movies: LazyPagingItems<MovieEntity>,
    onIntent: (MovieBookmarkIntent) -> Unit,
) {

    val isListEmpty = movies.loadState.refresh is LoadState.Error && movies.itemCount == 0

    if (isListEmpty) {
        EmptyOrErrorScreen(
            title = "No Data Found",
            info = "You haven’t bookmarked any movies yet. Start exploring and save your favorites to see them",
            primaryLabel = "Add Movie to Bookmark",
            action = {
                onIntent(MovieBookmarkIntent.OnNavigateToMovie)
            }
        )
    } else {

        LazyVerticalGrid(
            modifier = modifier
                .fillMaxSize()
                .padding(PaddingValues(12.dp)),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            columns = GridCells.Fixed(2)
        ) {
            items(
                count = movies.itemCount,
                key = movies.itemKey { it.toMovieUIModel().movie.id }
            ) { index ->
                val movie = movies[index]
                movie?.let {
                    BookmarkMovieItem(
                        movieUi = movie.toMovieUIModel(),
                        onIntent = onIntent
                    )
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

