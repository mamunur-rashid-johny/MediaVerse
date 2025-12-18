package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import com.johny.mediaverse.domain.model.podcast.Podcast
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PodcastBookmarkRoute(
    modifier: Modifier = Modifier,
    onItemClick: (Podcast) -> Unit,
    onNavigateToPodcast: () -> Unit
) {
    val viewModel: PodcastBookmarkViewModel = koinViewModel()
    val scope = rememberCoroutineScope()
    val savedPodcast = viewModel.savedPodcast.collectAsLazyPagingItems()

    ObserveAsEvent(
        events = viewModel.effect
    ) {
        when (it) {
            is PodcastBookmarkEffect.OnNavigateToPodcastDetailEffect -> {
                onItemClick(it.podcast)
            }

            PodcastBookmarkEffect.PodcastScreenNavigationEffect -> {
                onNavigateToPodcast()
            }

            is PodcastBookmarkEffect.ShowMessageEffect -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = "${it.podcast.title} is removed!",
                            action = SnackbarAction(
                                name = "Undo",
                                action = {
                                    viewModel.onIntent(
                                        PodcastBookmarkIntent.UndoPodcastIntent(it.podcast)
                                    )
                                }
                            )
                        )
                    )
                }
            }
        }
    }

    PodcastBookmarkScreen(
        modifier = modifier,
        podcast = savedPodcast,
        onIntent = viewModel::onIntent
    )
}

