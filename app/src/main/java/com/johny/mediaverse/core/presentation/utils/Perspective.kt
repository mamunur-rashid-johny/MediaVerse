package com.johny.mediaverse.core.presentation.utils

import androidx.compose.ui.graphics.Color

/**
 * Created by Johny on 16/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */
sealed class Perspective(
) {
    data class Left(
        val bottomEdgeColor: Color, val rightEdgeColor: Color
    ) : Perspective()

    data class Right(
        val topEdgeColor: Color, val leftEdgeColor: Color
    ) : Perspective()

    data class Top(
        val bottomEdgeColor: Color
    ) : Perspective()
}