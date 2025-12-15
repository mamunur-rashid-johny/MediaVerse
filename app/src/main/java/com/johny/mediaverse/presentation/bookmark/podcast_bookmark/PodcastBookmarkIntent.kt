package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface PodcastBookmarkIntent {
    data class OnPodcastBookmarkClickIntent(val podcast: Podcast) : PodcastBookmarkIntent
    data class OnPodcastBookRemoveIntent(val podcastId: String) : PodcastBookmarkIntent
    data object OnNavigateToPodcast: PodcastBookmarkIntent
}