package com.johny.mediaverse.presentation.podcast

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface PodcastEffect {
    data class NavigateToDetail(val podcast: Podcast): PodcastEffect
}