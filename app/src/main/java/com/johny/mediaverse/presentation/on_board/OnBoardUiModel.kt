package com.johny.mediaverse.presentation.on_board

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.johny.mediaverse.R

/**
 * Created by Johny on 28/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
data class OnBoardUiModel(
    @param:DrawableRes val imageId: Int,
    @param:StringRes val titleId: Int,
    @param:StringRes val descriptionId: Int
)

val dataSets = listOf(
    OnBoardUiModel(
        imageId = R.drawable.movie_on_board,
        titleId = R.string.movie_on_board_title,
        descriptionId = R.string.movie_on_board_description
    ),
    OnBoardUiModel(
        imageId = R.drawable.tv_show_on_board,
        titleId = R.string.tv_show_on_board_title,
        descriptionId = R.string.tv_show_on_board_description
    ),
    OnBoardUiModel(
        imageId = R.drawable.podcast_on_board,
        titleId = R.string.podcast_on_board_title,
        descriptionId = R.string.podcast_on_board_description
    )
)
