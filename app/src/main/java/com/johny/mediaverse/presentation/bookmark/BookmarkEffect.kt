package com.johny.mediaverse.presentation.bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface BookmarkEffect {
    data class NavigateToDetails(val podcast: Podcast) : BookmarkEffect
}