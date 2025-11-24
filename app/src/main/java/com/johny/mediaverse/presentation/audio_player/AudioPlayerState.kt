package com.johny.mediaverse.presentation.audio_player

import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel

data class AudioPlayerState(
    val episodeModel: EpisodeModel? = null,
    val isPlaying: Boolean = false,
    val isBuffering: Boolean = false,
    val currentPosition: Long = 0L,
    val duration: Long = 0L,
    val bufferedPosition: Long = 0L
)
