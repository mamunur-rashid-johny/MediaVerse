package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowBookmarkRoute(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onNavigateToTvShow: () -> Unit
) {
    val viewModel: TvShowBookmarkViewModel = koinViewModel()
    val tvShowPager = viewModel.savedTvShow.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is TvShowBookmarkEffect.OnNavigateToTvShowDetailEffect -> {
                onItemClick(effect.tvShowId)
            }

            TvShowBookmarkEffect.TvShowScreenNavigationEffect -> {
                onNavigateToTvShow()
            }
        }
    }

    TvShowBookmarkScreen(
        modifier = modifier,
        tvShows = tvShowPager,
        onIntent = viewModel::onIntent
    )
}

