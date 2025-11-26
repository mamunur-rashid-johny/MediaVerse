package com.johny.mediaverse.utils

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

/**
 * Converts a millisecond timestamp (Long) into a locale-sensitive, long-format date string.
 *
 * It uses the modern java.time API (JSR 310) which is safe, thread-safe, and locale-aware
 * (available via API desugaring on all Android versions).
 *
 * @return The formatted date string (e.g., "May 12, 2018") or null if an error occurs.
 */
fun Long.toDateString(): String? {
    return try {
        val instant = Instant.ofEpochMilli(this)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter
            .ofLocalizedDate(FormatStyle.LONG)
            .withLocale(Locale.getDefault())
        zonedDateTime.format(formatter)
    }catch (ex: Exception){
        ex.printStackTrace()
        null
    }
}


/*
*Pattern explanation:
// MMM   = Short Month (May)
// dd    = Day (12)
// yyyy  = Year (2018)
// 'at'  = Literal text " at "
// hh    = Hour in 12-hour format (11)
// .     = The dot separator you requested
// mm    = Minute (00)
// a     = AM/PM marker
* @return format of time mils to May 12, 2018 at 11.00 AM
* */
fun Long.toDateFormatString(): String{
    return try {
        val instant = Instant.ofEpochMilli(this)
        val zoneDateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy 'at' hh.mm a", Locale.US)
        zoneDateTime.format(formatter)
    }catch (_: Exception){
        ""
    }
}

fun Int.toSecondToMinute(): String {
    val minutes = this / 60
    return String.format(Locale.getDefault(),"%02d", minutes)
}

fun Modifier.shimmerEffect(): Modifier = composed {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.9f),
    )

    val transition = rememberInfiniteTransition(label = "ShimmerTransition")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "ShimmerAnimation"
    )

    background(
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnim.value - 200, y = translateAnim.value),
            end = Offset(x = translateAnim.value + 200, y = translateAnim.value + 500)
        )
    )
}

fun CharSequence.trimTrailingWhitespace(): CharSequence {
    var i = this.length
    while (i > 0 && Character.isWhitespace(this[i - 1])) {
        i--
    }
    return this.subSequence(0, i)
}

fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds)
}