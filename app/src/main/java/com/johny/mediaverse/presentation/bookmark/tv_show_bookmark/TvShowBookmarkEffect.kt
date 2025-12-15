package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

sealed interface TvShowBookmarkEffect {
    data class OnNavigateToTvShowDetailEffect(val tvShowId: Int) : TvShowBookmarkEffect
    data object TvShowScreenNavigationEffect: TvShowBookmarkEffect
}