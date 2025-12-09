package com.johny.mediaverse.presentation.bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface BookmarkIntent {
    data class OnNavigateToMovieDetails(val movieId: Int) : BookmarkIntent
    data class OnNavigateToTvShowDetails(val tvShowId: Int) : BookmarkIntent
    data class OnNavigateToPodcastDetails(val podcast: Podcast) : BookmarkIntent
    data class OnUpdateTabIndex(val index: Int) : BookmarkIntent
}