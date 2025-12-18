package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.presentation.bookmark.movie_bookmark.MovieBookmarkEffect

sealed interface PodcastBookmarkEffect {
    data class OnNavigateToPodcastDetailEffect(val podcast: Podcast) : PodcastBookmarkEffect
    data object PodcastScreenNavigationEffect: PodcastBookmarkEffect
    data class ShowMessageEffect(val podcast: Podcast): PodcastBookmarkEffect
}