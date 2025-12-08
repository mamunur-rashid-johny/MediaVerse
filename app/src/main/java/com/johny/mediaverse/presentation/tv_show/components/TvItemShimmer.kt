package com.johny.mediaverse.presentation.tv_show.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.utils.shimmerEffect

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TvShowLoadingRow() {
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
fun TvShowErrorRow(
    message: String?,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message?:"Failed to load more.",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Button(onClick = onClickRetry) {
            Text(
                text = "Retry",
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    MediaVerseTheme {
        TvShowErrorRow(message = "Failed to load more.") { }
    }
}


@Composable
fun TvShowItemShimmer(
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