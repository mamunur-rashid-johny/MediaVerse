package com.johny.mediaverse.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.presentation.ui.theme.addToBookmarkButtonBg
import com.johny.mediaverse.presentation.ui.theme.elevatedButtonBg
import com.johny.mediaverse.presentation.ui.theme.noDataFoundBackground


@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String,
    info: String,
    label: String,
    action: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .border(2.dp, noDataFoundBackground)
                .background(addToBookmarkButtonBg)
                .padding(24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold, fontSize = 28.sp, color = Color.Black
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = info,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray, fontSize = 16.sp, lineHeight = 22.sp
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            HorizontalDivider(color = Color.Black, thickness = 2.dp)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


                ThreeDimensionalLayout(
                    perspective = Perspective.Left(
                        bottomEdgeColor = Color.Black,
                        rightEdgeColor = Color.Black
                    ),
                    edgeOffset = 6.dp,
                    onClick = action
                ) {

                    Box(
                        modifier = Modifier
                            .border(2.dp, Color.Black)
                            .background(elevatedButtonBg)
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Black
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EmptyScreenPreviewTest() {
    MediaVerseTheme {
        EmptyScreen(
            title = "No Data Found",
            info = "You haven’t bookmarked any movies yet. Start exploring and save your favorites to see them",
            label = "Add Movie to Bookmark",
            action = {}
        )
    }
}