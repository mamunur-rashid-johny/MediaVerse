package com.johny.mediaverse.presentation.bookmark

/**
 * Created by Johny on 20/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SegmentedTabs(
    items: List<String>,
    colors: List<Color>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = isSystemInDarkTheme()
    val containerColor = if (isDark) Color(0xFF2D3748) else Color(0xFFEEEEEE)
    val unselectedTextColor = if (isDark) Color(0xFFA0AEC0) else Color.DarkGray
    val selectedTextColor = Color.White // Contrasts well with the provided bright colors

    BoxWithConstraints(
        modifier = modifier
            .height(44.dp)
            .background(color = containerColor, shape = CircleShape)
            .padding(4.dp)
    ) {
        val tabWidth = maxWidth / items.size

        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "indicatorOffset"
        )


        val currentIndicatorColor by animateColorAsState(
            targetValue = colors.getOrElse(selectedIndex) { MaterialTheme.colorScheme.primary },
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "indicatorColor"
        )

        Surface(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(tabWidth)
                .fillMaxHeight(),
            shape = CircleShape,
            color = currentIndicatorColor,
            shadowElevation = if (isDark) 0.dp else 2.dp // Shadows often look better disabled or reduced in dark mode
        ) {}

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, text ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { onItemSelected(index) }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = text,
                        color = if (selectedIndex == index) selectedTextColor else unselectedTextColor,
                        fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Normal,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}