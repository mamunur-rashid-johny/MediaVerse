package com.johny.mediaverse.presentation.podcast_details.components

import android.widget.TextView
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.utils.shimmerEffect
import com.johny.mediaverse.utils.toDateString
import com.johny.mediaverse.utils.toSecondToMinute
import com.johny.mediaverse.utils.trimTrailingWhitespace

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
            .padding(16.dp)
            ) {
        Text(
            text = episodeModel.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )

        HtmlText(
            html = episodeModel.description,
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
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
                    .fillMaxWidth(0.7f) // Simulate varying title lengths
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(14.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(14.dp)
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
    maxLines: Int = Int.MAX_VALUE
) {
    val linkColor = MaterialTheme.colorScheme.primary.toArgb()
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()
    val formattedText = remember(html) {
        val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT)
        spanned.trimTrailingWhitespace()
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            TextView(context).apply {
                movementMethod = null
                setTextColor(textColor)
                setLinkTextColor(linkColor)
                textSize = 16f
                setLineSpacing(12f, 1.1f)
            }
        },
        update = { textView ->
            textView.text = formattedText
            textView.maxLines = maxLines
        }
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

        EpisodeItem( episodeModel = dummyEpisode) {}
    }
}

@Preview
@Composable
private fun EpisodeItemShimmerPreview() {
    MediaVerseTheme {
        EpisodeItemShimmer()
    }
}


