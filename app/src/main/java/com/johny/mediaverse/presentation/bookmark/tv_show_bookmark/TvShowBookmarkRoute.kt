package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

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
fun TvShowBookmarkRoute(
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    onNavigateToTvShow: () -> Unit
) {
    val viewModel: TvShowBookmarkViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    val tvShowPager = viewModel.savedTvShow.collectAsLazyPagingItems()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is TvShowBookmarkEffect.OnNavigateToTvShowDetailEffect -> {
                onItemClick(effect.tvShowId)
            }

            TvShowBookmarkEffect.TvShowScreenNavigationEffect -> {
                onNavigateToTvShow()
            }

            is TvShowBookmarkEffect.ShowMessageEffect -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        event = SnackbarEvent(
                            message = "${effect.tvShowModel.title} is Removed!",
                            action = SnackbarAction(
                                name = "Undo",
                                action = {
                                    viewModel.onIntent(TvShowBookmarkIntent.UndoTvShowIntent(effect.tvShowModel))
                                }
                            )
                        )
                    )
                }
            }
        }
    }

    TvShowBookmarkScreen(
        modifier = modifier,
        tvShows = tvShowPager,
        onIntent = viewModel::onIntent
    )
}

