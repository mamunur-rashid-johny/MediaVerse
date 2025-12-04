package com.johny.mediaverse.presentation.podcast_details

import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel

sealed interface PodcastDetailsIntent {
    data class OnAudioPlayIntent(val episodeModel: EpisodeModel) : PodcastDetailsIntent
    data object OnBackPressed : PodcastDetailsIntent
    data class NavigateToWebviewIntent(val url: String?, val title: String) : PodcastDetailsIntent
}