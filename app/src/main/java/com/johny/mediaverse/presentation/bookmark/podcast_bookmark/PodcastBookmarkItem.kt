package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

/**
 * Created by Johny on 18/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun PodcastBookmarkItem(
    podcastUi: PodcastUIModel,
    onIntent:(PodcastBookmarkIntent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onIntent(PodcastBookmarkIntent.OnPodcastBookmarkClickIntent(podcastUi.podcast))
            }
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val modelLambda = remember(podcastUi.podcast.thumbnail) { { podcastUi.podcast.thumbnail } }
        CoilImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(12.dp)),
            imageModel = modelLambda,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = painterResource(R.drawable.podcast_placeholder)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = podcastUi.podcast.title,
                style = AppTypography.titleLarge,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = podcastUi.podcast.publisher,
                style = AppTypography.titleSmall,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
        }

        IconButton(onClick = {
            val intent = PodcastBookmarkIntent.OnPodcastBookRemoveIntent(podcastUi.podcast)
            onIntent(intent)
        }) {
            val (icon, tint) = if (podcastUi.isBookmark) {
                Icons.Default.BookmarkRemove to MaterialTheme.colorScheme.primary
            } else {
                Icons.Default.BookmarkBorder to MaterialTheme.colorScheme.onSurfaceVariant
            }
            Icon(
                imageVector = icon,
                contentDescription = if (podcastUi.isBookmark) "Remove Bookmark" else "Add Bookmark",
                modifier = Modifier.size(26.dp),
                tint = tint
            )
        }
    }
}


@Preview
@Composable
private fun PodcastItemPreview() {

    MediaVerseTheme {
        PodcastBookmarkItem(
            podcastUi = PodcastUIModel(
                podcast = Podcast(
                    "take",
                    "Apple TV / Blanchard House",
                    "https://cdn-images-3.listennotes.com/podcasts/adrift-Jgw_l1G49SB-dX0Me-t8_sq.300x300.jpg",
                    "Adrift",
                    120,
                    1689888000,
                    "United States",
                    "English",
                    "episodic",
                    120
                ),
                isBookmark = false
            )
        ){

        }
    }
}