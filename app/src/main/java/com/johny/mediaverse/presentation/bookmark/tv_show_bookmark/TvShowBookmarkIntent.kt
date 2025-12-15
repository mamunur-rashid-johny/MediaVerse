package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

sealed interface TvShowBookmarkIntent {
    data class OnTvShowBookmarkClickIntent(val tvShowId: Int) : TvShowBookmarkIntent
    data class OnTvShowBookRemoveIntent(val tvShowId: Int) : TvShowBookmarkIntent
    data object OnNavigateToTvShow: TvShowBookmarkIntent
}