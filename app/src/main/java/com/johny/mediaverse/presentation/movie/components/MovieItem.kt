package com.johny.mediaverse.presentation.movie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.model.movie.config.PosterSize
import com.johny.mediaverse.presentation.movie.model.MovieModelUi
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage


@Composable
fun MovieGridItem(
    modifier: Modifier = Modifier,
    movieUi: MovieModelUi,
    onItemClick: (MovieModel) -> Unit,
    onAddBookmark: (MovieModel) -> Unit,
    onRemoveBookmark: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(movieUi.movie) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .clip(RoundedCornerShape(15.dp))
        ) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + movieUi.movie.posterPath }, // W500 is better for grids
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                previewPlaceholder = painterResource(R.drawable.movie_tv_placeholder)
            )

            // Floating Bookmark Button (Top Right)
            IconButton(
                onClick = {
                    if (movieUi.isBookmark) {
                        onRemoveBookmark(movieUi.movie.id)
                    } else {
                        onAddBookmark(movieUi.movie)
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    // Add a semi-transparent background so it's visible on light/dark posters
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = if (movieUi.isBookmark) Icons.Default.BookmarkRemove else Icons.Default.BookmarkBorder,
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(18.dp),
                    tint = if (movieUi.isBookmark) Color.Red else Color.White
                )
            }
        }

        // Text Info Section
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = movieUi.movie.title,
                style = AppTypography.titleMedium, // Slightly smaller for grid
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${movieUi.movie.year} • ✰ ${movieUi.movie.rating}",
                style = AppTypography.bodySmall,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview
@Composable
private fun MovieGridPreview() {
    MediaVerseTheme {
        val dummy = MovieModelUi(
            movie = MovieModel(
                id = 1,
                title = "The Lost Horizon",
                rating = 7.8,
                releaseDate = "2021-05-14",
                posterPath = "/images/posters/lost_horizon.jpg"
            ),
            isBookmark = false
        )
        MovieGridItem(
            movieUi = dummy,
            onItemClick = {},
            onAddBookmark = {}
        ) { }
    }
}