package com.johny.mediaverse.presentation.podcast_details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.data.mapper.toPodcastHeaderDetails
import com.johny.mediaverse.presentation.podcast_details.components.EpisodeItem
import com.johny.mediaverse.presentation.podcast_details.components.EpisodeItemShimmer
import com.johny.mediaverse.presentation.podcast_details.components.PodcastDetailsHeader
import com.johny.mediaverse.presentation.podcast_details.components.PodcastDetailsHeaderShimmer
import com.johny.mediaverse.utils.shimmerEffect


@Composable
fun PodcastDetailsScreen(
    modifier: Modifier = Modifier,
    state: PodcastDetailsState,
    onIntent: (PodcastDetailsIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isLoading || state.podcastDetails == null) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .shimmerEffect()
                        )
                        Box(
                            modifier = Modifier
                                .height(30.dp)
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
                item { PodcastDetailsHeaderShimmer() }
                items(10) {
                    EpisodeItemShimmer()
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                IconButton(onClick = {
                    onIntent(PodcastDetailsIntent.OnBackPressed)
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
                Text(
                    text = "Podcast Details",
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    PodcastDetailsHeader(
                        headerData = state.podcastDetails.toPodcastHeaderDetails(),
                        onErrorMessage = {},
                        onPlayLatestEpisode = {
                            onIntent(PodcastDetailsIntent.OnAudioPlayIntent(it))
                        },
                        onWebsiteLinkPress = { url ->
                            onIntent(PodcastDetailsIntent.NavigateToWebviewIntent(url = url, title = state.podcastDetails.title))
                        }
                    )
                }
                itemsIndexed(state.podcastDetails.episodes) { index, episode ->
                    EpisodeItem(
                        episodeModel = episode,
                        onClick = { onIntent(PodcastDetailsIntent.OnAudioPlayIntent(it)) })
                    if (index < state.podcastDetails.episodes.lastIndex) {
                        HorizontalDivider()
                    }
                }
            }
        }
    }

}