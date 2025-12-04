package com.johny.mediaverse.presentation.podcast_details

import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel

sealed interface PodcastDetailsEffect {
    data class NavigateToAudioPlayer(val episodeModel: EpisodeModel) : PodcastDetailsEffect
    data object OnBackPressed : PodcastDetailsEffect
    data class NavigateToWebviewEffect(val url: String?, val title: String) : PodcastDetailsEffect
}