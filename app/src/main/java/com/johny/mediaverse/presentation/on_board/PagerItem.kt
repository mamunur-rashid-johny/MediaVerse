package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

/**
 * Created by Johny on 28/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

@Composable
fun PagerItem(
    modifier: Modifier = Modifier,
    onBoardUiModel: OnBoardUiModel
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        CoilImage(
            modifier = Modifier
                .aspectRatio(1f).padding(horizontal = 16.dp),
            imageModel = { onBoardUiModel.imageId },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )

        Spacer(
            modifier = Modifier.size(10.dp)
        )
        Text(
            text = stringResource(onBoardUiModel.titleId),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(onBoardUiModel.descriptionId),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center
        )
    }

}


@Preview
@Composable
private fun PagerItemPreview() {
    val dummy = dataSets[0]
    MediaVerseTheme {
        PagerItem(
            modifier = Modifier.fillMaxSize(),
            onBoardUiModel = dummy
        )
    }
}