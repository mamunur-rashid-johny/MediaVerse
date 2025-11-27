package com.johny.mediaverse.presentation.movie.components

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.model.movie.config.PosterSize
import com.johny.mediaverse.presentation.movie.model.MovieModelUi
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movieUi: MovieModelUi,
    onItemClick: (MovieModel) -> Unit,
    onAddBookmark: (MovieModel) -> Unit,
    onRemoveBookmark: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(movieUi.movie) }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        CoilImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp)),
            imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W92.value + movieUi.movie.posterPath },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            previewPlaceholder = painterResource(R.drawable.movie_tv_placeholder)
        )

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = movieUi.movie.title,
                style = AppTypography.titleLarge,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
            Text(
                text = "",
                style = AppTypography.titleSmall,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
            )
        }

        IconButton(onClick = {
            if (movieUi.isBookmark) {
                onRemoveBookmark(movieUi.movie.id)
            } else {
                onAddBookmark(movieUi.movie)
            }
        }) {
            Icon(
                imageVector = if (movieUi.isBookmark) Icons.Default.BookmarkRemove else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                modifier = Modifier.size(24.dp),
                tint = if (movieUi.isBookmark) Color.Red else Color.Green
            )
        }

    }
}