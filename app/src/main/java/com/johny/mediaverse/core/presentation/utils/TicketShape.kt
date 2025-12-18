package com.johny.mediaverse.core.presentation.utils

/**
 * Created by Johny on 17/12/25.
 * Copyright (c) 2025 Pathao Ltd. All rights reserved.
 */

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection

class TicketShape(
    private val cornerRadius: Dp
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = Path().apply {
                val radius = with(density) { cornerRadius.toPx() }
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, (size.height / 2) - radius)
                arcTo(
                    rect = Rect(
                        left = size.width - radius,
                        top = (size.height / 2) - radius,
                        right = size.width + radius,
                        bottom = (size.height / 2) + radius
                    ),
                    startAngleDegrees = 270f,
                    sweepAngleDegrees = -180f,
                    forceMoveTo = false
                )
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                lineTo(0f, (size.height / 2) + radius)
                arcTo(
                    rect = Rect(
                        left = -radius,
                        top = (size.height / 2) - radius,
                        right = radius,
                        bottom = (size.height / 2) + radius
                    ),
                    startAngleDegrees = 90f,
                    sweepAngleDegrees = -180f,
                    forceMoveTo = false
                )
                close()
            }
        )
    }
}