package com.johny.mediaverse.presentation.podcast.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun PodcastItem(
    podcast: Podcast,
    onItemClick: (Podcast) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(podcast) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        )
    {
        CoilImage(
            modifier = Modifier.size(60.dp).clip(RoundedCornerShape(15.dp)),
            imageModel = { podcast.thumbnail },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = painterResource(R.drawable.placeholder)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column {
            Text(
                text = podcast.title,
                style = AppTypography.titleLarge,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
            Text(
                text = podcast.publisher,
                style = AppTypography.titleSmall,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
        }


    }

}


@Preview
@Composable
private fun PodcastItemPreview() {

    MediaVerseTheme {
        PodcastItem(
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
            onItemClick = {}
        )
    }
}