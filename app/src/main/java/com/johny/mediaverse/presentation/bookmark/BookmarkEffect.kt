package com.johny.mediaverse.presentation.bookmark

import com.johny.mediaverse.domain.model.podcast.Podcast

sealed interface BookmarkEffect {
    data class NavigateToPodcastDetails(val podcast: Podcast) : BookmarkEffect
    data class NavigateToMovieDetails(val movieId: Int) : BookmarkEffect
    data class NavigateToTvShowDetails(val tvShowId: Int) : BookmarkEffect
}