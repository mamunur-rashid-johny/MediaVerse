package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieBookmarkRoute(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onNavigateToMovie: () -> Unit
) {
    val viewModel: MovieBookmarkViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    val moviePager = viewModel.savedMovie.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is MovieBookmarkEffect.OnNavigateToMovieDetailEffect -> {
                onItemClick(effect.movieId)
            }

            MovieBookmarkEffect.MovieScreenNavigationEffect -> {
                onNavigateToMovie()
            }

            is MovieBookmarkEffect.ShowMessageEffect -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "${effect.movieModel.title} is removed!",
                            action = SnackbarAction(
                                name = "Undo",
                                action = {
                                    viewModel.onIntent(
                                        MovieBookmarkIntent.ONMovieBookmarkUndoIntent(
                                            effect.movieModel
                                        )
                                    )
                                }
                            )
                        )
                    )
                }
            }
        }
    }


    MovieBookmarkScreen(
        modifier = modifier,
        movies = moviePager,
        onIntent = viewModel::onIntent
    )

}

