package com.johny.mediaverse.presentation.bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface BookmarkIntent {
    data class OnNavigateToDetails(val podcast: Podcast) : BookmarkIntent
    data class OnRemoveBookmark(val podcastId: String) : BookmarkIntent
}