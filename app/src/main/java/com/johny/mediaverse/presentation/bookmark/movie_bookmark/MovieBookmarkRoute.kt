package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieBookmarkRoute(modifier: Modifier = Modifier,onItemClick:(Int)->Unit) {
    val viewModel: MovieBookmarkViewModel = koinViewModel()
    val moviePager = viewModel.savedMovie.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is MovieBookmarkEffect.OnNavigateToMovieDetailEffect ->{
                onItemClick(effect.movieId)
            }
        }
    }


    MovieBookmarkScreen(
        modifier = modifier,
        movies = moviePager,
        onIntent = viewModel::onIntent
    )

}

