package com.johny.mediaverse.presentation.podcast_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Podcasts
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import com.johny.mediaverse.domain.model.podcast_details.PodcastHeaderDetails
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.utils.shimmerEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun PodcastDetailsHeader(
    modifier: Modifier = Modifier,
    headerData: PodcastHeaderDetails,
    onPlayLatestEpisode: (EpisodeModel) -> Unit,
    onWebsiteLinkPress: (String?) -> Unit,
    onErrorMessage: (String) -> Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CoilImage(
            modifier = Modifier
                .padding(top = 40.dp)
                .size(180.dp)
                .clip(RoundedCornerShape(10.dp)),
            imageModel = { headerData.thumbnail },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = painterResource(R.drawable.podcast_placeholder)
        )

        Text(
            text = headerData.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp),
            textAlign = TextAlign.Center
        )


        Text(
            text = headerData.publisher,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            textAlign = TextAlign.Center
        )

        HtmlText(
            html = headerData.description,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            onLinkClick = {

            }
        )

        //play button and website link
        FlowRow(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
            ElevatedButton(
                onClick = {
                    if (headerData.latestEpisode != null) onPlayLatestEpisode(headerData.latestEpisode) else onErrorMessage(
                        "No latest audio available"
                    )
                },
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp
                )
            ) {

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = stringResource(R.string.play_latest_episode),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(R.string.play_latest_episode),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1
                )
            }

            ElevatedButton(
                onClick = {
                    if (headerData.website != null) onWebsiteLinkPress(headerData.website) else onErrorMessage(
                        "No website available"
                    )
                },
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Podcasts,
                    contentDescription = stringResource(R.string.play_latest_episode),
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = stringResource(R.string.visit_website),
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1
                )
            }
        }


        if (headerData.totalEpisodes > 0) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.recent_episodes),
                    style = MaterialTheme.typography.titleLarge
                )

                CircularBadge(
                    text = "${headerData.totalEpisodes}",
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

}


@Composable
fun PodcastDetailsHeaderShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .padding(top = 40.dp)
                .size(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .height(28.dp) // Height of HeadlineMedium
                .fillMaxWidth(0.7f) // Width simulation
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(16.dp)
                .fillMaxWidth(0.4f)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(140.dp)
                    .clip(CircleShape) // Buttons usually have full rounded corners
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(40.dp)
                    .width(140.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(24.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
    }
}

@Composable
fun CircularBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color = Color.White,
    size: Dp = 30.dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size)
            .background(color = backgroundColor, shape = CircleShape)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview
@Composable
private fun PodcastDetailsHeaderPreview() {
    MediaVerseTheme {

        val dummyEpisode = EpisodeModel(
            id = "ep_001",
            title = "The Rise of AI-Driven Innovation",
            description = "In this episode, we explore how artificial intelligence is reshaping industries, " +
                    "from healthcare to entertainment. Featuring insights from leading experts.",
            audio = "https://example.com/audio/episode_001.mp3",
            thumbnail = "https://example.com/images/episode_001.jpg",
            pubDate = 1732406400L, // Example Unix timestamp
            audioLengthSec = 2420 // ~40 minutes
        )
        val dummyPodcastHeader = PodcastHeaderDetails(
            id = "pod12345",
            title = "The Future Insights Podcast",
            publisher = "Insight Media Group",
            thumbnail = "https://example.com/images/podcast_thumbnail.jpg",
            totalEpisodes = 18,
            description = "A weekly deep dive into technology, science, and culture with industry experts.",
            language = "en",
            country = "US",
            website = "https://futureinsights.example.com",
            nextEpisodePubDate = 1732406400L,
            latestEpisode = dummyEpisode
        )

        PodcastDetailsHeader(
            modifier = Modifier.fillMaxWidth(),
            headerData = dummyPodcastHeader,
            onPlayLatestEpisode = {},
            onWebsiteLinkPress = {},
            onErrorMessage = {}
        )
    }
}

@Preview
@Composable
private fun PodcastHeaderDetailsShimmerPreview() {
    MediaVerseTheme {
        PodcastDetailsHeaderShimmer()
    }
}