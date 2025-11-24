package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme

@Composable
fun OnBoardScreen(
    modifier: Modifier = Modifier,
    onBoardState: OnBoardState,
    onBoardEvent: (OnBoardIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Welcome to OnBoard Screen",
            style = AppTypography.bodyLarge
        )

        Spacer(modifier = Modifier.size(40.dp))

        NextOrGetStartedButton(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            label = "Home Screen"
        ) {onBoardEvent(OnBoardIntent.SaveOnBoardIntent) }
    }
}

@Preview
@Composable
private fun OnBoardScreenPreview() {
    MediaVerseTheme {
        val state = OnBoardState()
        OnBoardScreen(
            modifier = Modifier.fillMaxSize(),
            onBoardState = state,
            onBoardEvent = {}
        )
    }
}