package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.presentation.ui.theme.AppTypography
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme

@Composable
fun NextOrGetStartedButton(
    modifier: Modifier = Modifier,
    label: String,
    onPress: () -> Unit
) {
    ElevatedButton(
        onClick = {
            onPress()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFCDE7BE),
        ),
        modifier = modifier
    ) {
        Text(
            text = label,
            style = AppTypography.labelLarge.copy(
                fontSize = 16.sp
            )
        )
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