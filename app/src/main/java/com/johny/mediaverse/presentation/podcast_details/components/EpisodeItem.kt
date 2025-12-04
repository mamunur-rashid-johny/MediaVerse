package com.johny.mediaverse.presentation.podcast_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.utils.parseHtml
import com.johny.mediaverse.utils.shimmerEffect
import com.johny.mediaverse.utils.toDateString
import com.johny.mediaverse.utils.toSecondToMinute

@Composable
fun EpisodeItem(
    episodeModel: EpisodeModel,
    onClick: (EpisodeModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { onClick(episodeModel) }
            )
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        Text(
            text = episodeModel.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )



        Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "${episodeModel.audioLengthSec.toSecondToMinute()} min",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "\u00B7",
                style = MaterialTheme.typography.bodySmall,
            )
            Text(
                text = "${episodeModel.pubDate.toDateString()}",
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}


@Composable
fun EpisodeItemShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth() // Simulate varying title lengths
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )


            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier,
    style: TextStyle,
    linkColor: Color = MaterialTheme.colorScheme.primary,
    textAlign: TextAlign? = null,
    onLinkClick: ((String) -> Unit)? = null
) {
    val annotatedString = remember(html, onLinkClick) {
        html.parseHtml(linkColor, onLinkClick)
    }

    Text(
        modifier = modifier,
        text = annotatedString,
        style = style,
        textAlign = textAlign
    )
}


@Preview
@Composable
private fun EpisodeItemPreview() {
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

        EpisodeItem(episodeModel = dummyEpisode) {}
    }
}

@Preview
@Composable
private fun EpisodeItemShimmerPreview() {
    MediaVerseTheme {
        EpisodeItemShimmer()
    }
}


