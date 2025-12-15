package com.johny.mediaverse.presentation.movie.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.core.presentation.components.Perspective
import com.johny.mediaverse.core.presentation.components.ThreeDimensionalLayout
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.presentation.ui.theme.addToBookmarkButtonBg
import com.johny.mediaverse.presentation.ui.theme.elevatedButtonBg
import com.johny.mediaverse.presentation.ui.theme.noDataFoundBackground
import com.johny.mediaverse.utils.shimmerEffect

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        LoadingIndicator(
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
fun ErrorRow(
    message: String?,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, noDataFoundBackground)
            .background(addToBookmarkButtonBg)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message?:"Failed to load more.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black
            ),
            modifier = Modifier.weight(1f)
        )
        ThreeDimensionalLayout(
            perspective = Perspective.Left(
                bottomEdgeColor = Color.Black,
                rightEdgeColor = Color.Black
            ),
            edgeOffset = 6.dp,
            onClick = onClickRetry
        ) {

            Box(
                modifier = Modifier
                    .border(2.dp, Color.Black)
                    .background(elevatedButtonBg)
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Retry",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    MediaVerseTheme {
        ErrorRow(message = "Failed to load more.") { }
    }
}


@Composable
fun MovieGridItemShimmer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
                .shimmerEffect()
        ) {

            // Floating Bookmark Button (Top Right)
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    .size(32.dp)
                    .shimmerEffect()
            ) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .shimmerEffect(),
                )
            }
        }

        // Text Info Section
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 50.dp, height = 20.dp)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .size(width = 50.dp, height = 20.dp)
                    .shimmerEffect()
            )
        }
    }
}