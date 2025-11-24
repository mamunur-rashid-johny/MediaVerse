package com.johny.mediaverse.presentation.audio_player

sealed interface AudioPlayerIntent {
    data object OnBackPressed : AudioPlayerIntent
    data object OnPlayPauseToggle : AudioPlayerIntent
    data class OnSeek(val position: Long) : AudioPlayerIntent
    data object OnForward : AudioPlayerIntent
    data object OnRewind : AudioPlayerIntent
}