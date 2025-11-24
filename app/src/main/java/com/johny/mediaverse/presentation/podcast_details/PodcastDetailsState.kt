package com.johny.mediaverse.presentation.podcast_details

import com.johny.mediaverse.domain.model.podcast_details.PodcastDetails

data class PodcastDetailsState(
    val isLoading: Boolean = false,
    val podcastDetails: PodcastDetails? = null
)
