package com.johny.mediaverse.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.core.presentation.utils.TicketShape
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import kotlinx.coroutines.delay

/**
 * Created by Johny on 17/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

@Composable
fun ConnectivityBanner(
    modifier: Modifier = Modifier,
    isConnected: Boolean
) {
    var visibility by remember { mutableStateOf(ConnectionState.Hidden) }
    LaunchedEffect(isConnected) {
        if (!isConnected) {
            visibility = ConnectionState.Disconnected
        } else {
            if (visibility == ConnectionState.Disconnected) {
                visibility = ConnectionState.BackOnline
                delay(2000)
                visibility = ConnectionState.Hidden
            }
        }
    }
    AnimatedVisibility(
        visible = visibility != ConnectionState.Hidden,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut(),
        modifier = modifier
    ) {
        val backgroundColor by animateColorAsState(
            targetValue = if (visibility == ConnectionState.Disconnected)
                MaterialTheme.colorScheme.error
            else
                Color(0xFF4CAF50),
            animationSpec = tween(durationMillis = 500),
            label = "bg color anim"
        )


        val message =
            if (visibility == ConnectionState.Disconnected)
                "No Connection Available"
            else
                "Back Online"

        val icon = if (visibility == ConnectionState.Disconnected)
            Icons.Default.WifiOff
        else
            Icons.Default.Wifi

        Surface(
            color = backgroundColor,
            contentColor = Color.White,
            modifier = Modifier.fillMaxWidth(),
            shape = TicketShape(cornerRadius = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}

@Preview
@Composable
private fun ConnectivityBannerLostPreview() {
    MediaVerseTheme {
        ConnectivityBanner(
            isConnected = false
        )
    }
}

@Preview
@Composable
private fun ConnectivityBannerAvailablePreview() {
    MediaVerseTheme {
        ConnectivityBanner(
            isConnected = true
        )
    }
}

private enum class ConnectionState {
    Hidden,
    Disconnected,
    BackOnline
}