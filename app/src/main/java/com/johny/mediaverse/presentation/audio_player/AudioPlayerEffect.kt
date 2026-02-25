package com.johny.mediaverse.presentation.audio_player

sealed interface AudioPlayerEffect {
    data object OnBack : AudioPlayerEffect
    data object StartService : AudioPlayerEffect
    data object StopService : AudioPlayerEffect
}