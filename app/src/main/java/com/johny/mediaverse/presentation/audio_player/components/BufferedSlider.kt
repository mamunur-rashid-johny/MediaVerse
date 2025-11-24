package com.johny.mediaverse.presentation.audio_player.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BufferedSlider(
    modifier: Modifier = Modifier,
    currentPosition: Float,
    bufferedPosition: Float,
    duration: Float,
    onSeek: (Float) -> Unit,
    onSeekFinished: (Float) -> Unit
) {
    Box(
        modifier = modifier.height(48.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val bufferProgress = if (duration > 0) bufferedPosition / duration else 0f

        LinearProgressIndicator(
            progress = { bufferProgress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
                .height(4.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
            trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
        )

        Slider(
            value = currentPosition,
            onValueChange = onSeek,
            onValueChangeFinished = { onSeekFinished(currentPosition) },
            valueRange = 0f..duration,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}