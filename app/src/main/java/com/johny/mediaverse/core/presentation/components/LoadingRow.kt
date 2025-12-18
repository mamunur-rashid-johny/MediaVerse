package com.johny.mediaverse.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.johny.mediaverse.presentation.ui.theme.addToBookmarkButtonBg
import com.johny.mediaverse.presentation.ui.theme.noDataFoundBackground

/**
 * Created by Johny on 16/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, noDataFoundBackground)
            .background(addToBookmarkButtonBg)
            .padding(4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        LoadingIndicator(
            modifier = Modifier.size(32.dp),
            color = Color.White
        )
    }
}

@Preview
@Composable
private fun LoadingRowPreview() {
    MediaVerseTheme {
        LoadingRow()
    }
}

