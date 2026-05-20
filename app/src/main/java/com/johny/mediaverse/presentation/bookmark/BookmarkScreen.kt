package com.johny.mediaverse.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.core.navigation.LocalScaffoldPadding
import com.johny.mediaverse.presentation.bookmark.movie_bookmark.MovieBookmarkRoute
import com.johny.mediaverse.presentation.bookmark.podcast_bookmark.PodcastBookmarkRoute
import com.johny.mediaverse.presentation.bookmark.tv_show_bookmark.TvShowBookmarkRoute

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    state: BookmarkState,
    onIntent: (BookmarkIntent) -> Unit
) {
    val tabs = listOf("Movie", "Tv Show", "Podcast")
    val colors = listOf(Color(0xFFE53E3E), Color(0xFF3182CE), Color(0xFF38A169))
    val topInset = LocalScaffoldPadding.current.calculateTopPadding()

    Column(modifier = Modifier.padding(top = topInset)) {
        SegmentedTabs(
            items = tabs,
            colors = colors,
            selectedIndex = state.selectedTabIndex,
            onItemSelected = { index -> onIntent(BookmarkIntent.OnUpdateTabIndex(index)) },
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        when (state.selectedTabIndex) {
            0 -> MovieBookmarkRoute(
                modifier = modifier.weight(1f),
                onItemClick = {
                    onIntent(BookmarkIntent.OnNavigateToMovieDetails(it))
                },
                onNavigateToMovie = {
                    onIntent(BookmarkIntent.OnNavigateToMovie)
                }
            )

            1 -> TvShowBookmarkRoute(
                modifier = modifier.weight(1f),
                onItemClick = {
                    onIntent(BookmarkIntent.OnNavigateToTvShowDetails(it))
                },
                onNavigateToTvShow = {
                    onIntent(BookmarkIntent.OnNavigateToTvShow)
                }
            )

            2 -> PodcastBookmarkRoute(
                modifier = modifier.weight(1f),
                onItemClick = {
                    onIntent(BookmarkIntent.OnNavigateToPodcastDetails(it))
                },
                onNavigateToPodcast = {
                    onIntent(BookmarkIntent.OnNavigateToPodcast)
                }
            )
        }
    }
}