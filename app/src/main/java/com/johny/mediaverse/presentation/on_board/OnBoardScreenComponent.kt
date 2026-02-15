package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.core.presentation.components.ThreeDimenWithEvent
import com.johny.mediaverse.core.presentation.utils.Perspective
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.presentation.ui.theme.elevatedButtonBg

@Composable
fun NextOrGetStartedButton(
    modifier: Modifier = Modifier,
    label: String,
    onPress: () -> Unit
) {
    ThreeDimenWithEvent(
        perspective = Perspective.Left(
            bottomEdgeColor = Color.Black,
            rightEdgeColor = Color.Black
        ),
        edgeOffset = 6.dp,
        onClick = onPress,
        modifier = modifier
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
                .border(2.dp, Color.Black)
                .background(elevatedButtonBg)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
private fun NextOrGetStartedButtonPreview() {
    MediaVerseTheme {
        NextOrGetStartedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            label = "Get Started",
            onPress = {}
        )
    }
}