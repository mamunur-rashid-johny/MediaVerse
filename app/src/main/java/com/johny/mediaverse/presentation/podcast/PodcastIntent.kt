package com.johny.mediaverse.presentation.podcast

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface PodcastIntent {
    data class OnItemClick(val podcast: Podcast) : PodcastIntent
    data class OnAddBookMark(val podcast: Podcast) : PodcastIntent
    data class OnRemoveBookmark(val podcastId: String) : PodcastIntent
}