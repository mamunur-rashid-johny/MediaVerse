package com.johny.mediaverse.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.core.presentation.utils.Perspective
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.presentation.ui.theme.addToBookmarkButtonBg
import com.johny.mediaverse.presentation.ui.theme.elevatedButtonBg
import com.johny.mediaverse.presentation.ui.theme.noDataFoundBackground

/**
 * Created by Johny on 16/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

@Composable
fun ErrorRow(
    modifier: Modifier = Modifier,
    message: String?,
    onRetry: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, noDataFoundBackground)
            .background(addToBookmarkButtonBg)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message?:"Unknown Error",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        ThreeDimenWithEvent(
            perspective = Perspective.Left(
                bottomEdgeColor = Color.Black,
                rightEdgeColor = Color.Black
            ),
            edgeOffset = 6.dp,
            onClick = onRetry
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
        ErrorRow(message = "You are not connected to Internet try later to get the perfect result please connect to the internet for further go") { }
    }
}