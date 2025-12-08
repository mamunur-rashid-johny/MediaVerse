package com.johny.mediaverse.presentation.tv_show.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.config.PosterSize
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.utils.shimmerEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun TvShowItem(
    modifier: Modifier = Modifier,
    tvShowUi: TvShowUiModel,
    onItemClick: (TvShowModel) -> Unit,
    onAddBookmark: (TvShowModel) -> Unit,
    onRemoveBookmark: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(tvShowUi.tvShow) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                CoilImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + tvShowUi.tvShow.posterPath },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    previewPlaceholder = painterResource(R.drawable.movie_tv_placeholder),
                    failure = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(modifier = Modifier.fillMaxSize().shimmerEffect())
                        }
                    }
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(40.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            if (tvShowUi.isBookmarked) {
                                onRemoveBookmark(tvShowUi.tvShow.id)
                            } else {
                                onAddBookmark(tvShowUi.tvShow)
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Black.copy(alpha = 0.6f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (tvShowUi.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (tvShowUi.isBookmarked) "Remove from bookmarks" else "Add to bookmarks",
                        modifier = Modifier.size(18.dp),
                        tint = if (tvShowUi.isBookmarked) MaterialTheme.colorScheme.primary else Color.White
                    )
                }
            }
        }

        // Text Info Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 4.dp, end = 4.dp)
        ) {
            Text(
                text = tvShowUi.tvShow.title,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tvShowUi.tvShow.year,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color(0xFFFFC107)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = tvShowUi.tvShow.rating.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview
@Composable
private fun TvShowItemPreview() {
    MediaVerseTheme {
        val dummy = TvShowUiModel(
            tvShow = TvShowModel(
                id = 1,
                title = "The Lost Horizon",
                rating = 7.8,
                releaseDate = "2021-05-14",
                posterPath = "/images/posters/lost_horizon.jpg"
            ),
            isBookmarked = false
        )
        TvShowItem(
            tvShowUi = dummy,
            onItemClick = {},
            onAddBookmark = {}
        ) { }
    }
}