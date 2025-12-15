package com.johny.mediaverse.presentation.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    Column {
        PrimaryTabRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            selectedTabIndex = state.selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color(0xFF2D3748),
            indicator = {},
            divider = {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE2E8F0),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = state.selectedTabIndex == index,
                    onClick = { onIntent(BookmarkIntent.OnUpdateTabIndex(index)) },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (state.selectedTabIndex == index)
                                colors[index].copy(alpha = 0.1f)
                            else Color.Transparent
                        ),
                    enabled = true,
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = if (state.selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    },
                    selectedContentColor = colors[index],
                    unselectedContentColor = Color(0xFF4A5568),
                    interactionSource = remember { MutableInteractionSource() }
                )
            }
        }

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