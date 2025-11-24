package com.johny.mediaverse.presentation.audio_player

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.toRoute
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.utils.serializableType
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

class AudioPlayerViewModel(
    savedStateHandle: SavedStateHandle,
    private val player: ExoPlayer
) : ViewModel() {

    var state = MutableStateFlow(AudioPlayerState())
        private set

    private val _effect = MutableSharedFlow<AudioPlayerEffect>()
    val effect = _effect.asSharedFlow()

    init {

        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                state.update { it.copy(isPlaying = isPlaying) }
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                when(playbackState){
                    Player.STATE_READY ->{
                        state.update { it.copy(duration = player.duration.coerceAtLeast(0L)) }

                    }
                    Player.STATE_ENDED -> {
                        state.update {
                            it.copy(
                                isPlaying = false,
                                currentPosition = 0L
                            )
                        }
                        player.seekTo(0L)
                        player.pause()
                    }
                    Player.STATE_BUFFERING -> {
                        state.update { it.copy(isBuffering = true) }
                    }
                }
            }
        })

        try {
            val typeMap = mapOf(typeOf<EpisodeModel>() to serializableType<EpisodeModel>())
            val args = savedStateHandle.toRoute<Destination.AudioPlayerRoute>(typeMap)
            loadAudio(args.episodeModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        startProgressUpdateLoop()
    }

    fun onIntent(intent: AudioPlayerIntent) {
        when (intent) {
            is AudioPlayerIntent.OnPlayPauseToggle -> {
                if (player.isPlaying) player.pause() else player.play()
            }

            is AudioPlayerIntent.OnSeek -> {
                player.seekTo(intent.position)
                state.update { it.copy(currentPosition = intent.position) }
            }

            is AudioPlayerIntent.OnForward -> {
                player.seekTo(player.currentPosition + 10_000)
            }

            is AudioPlayerIntent.OnRewind -> {
                player.seekTo(player.currentPosition - 10_000)
            }

            is AudioPlayerIntent.OnBackPressed -> {
                player.stop()
                viewModelScope.launch { _effect.emit(AudioPlayerEffect.OnBack) }
            }
        }
    }

    private fun loadAudio(episode: EpisodeModel) {
        state.update {
            it.copy(
                episodeModel = episode,
                isPlaying = true,
                duration = episode.audioLengthSec.toLong() * 1000L
            )
        }

        val metadata = androidx.media3.common.MediaMetadata.Builder()
            .setTitle(episode.title)
            .setArtworkUri(android.net.Uri.parse(episode.thumbnail))
            .build()

        val mediaItem = MediaItem.Builder()
            .setUri(episode.audio)
            .setMediaMetadata(metadata)
            .build()

        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }

    private fun startProgressUpdateLoop() {
        viewModelScope.launch {
            while (isActive) {
                if (player.isPlaying) {
                    state.update {
                        it.copy(
                            currentPosition = player.currentPosition,
                            duration = player.duration.coerceAtLeast(1L),
                            bufferedPosition = player.bufferedPosition
                        )
                    }
                }
                delay(200L)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}