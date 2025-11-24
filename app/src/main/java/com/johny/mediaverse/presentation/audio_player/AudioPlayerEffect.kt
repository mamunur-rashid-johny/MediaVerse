package com.johny.mediaverse.presentation.audio_player

sealed interface AudioPlayerEffect {
    data object OnBack : AudioPlayerEffect
}