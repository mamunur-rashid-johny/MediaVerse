package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

import com.johny.mediaverse.domain.model.tv_show.TvShowModel

sealed interface TvShowBookmarkIntent {
    data class OnTvShowBookmarkClickIntent(val tvShowId: Int) : TvShowBookmarkIntent
    data class OnTvShowBookRemoveIntent(val tvShowModel: TvShowModel) : TvShowBookmarkIntent
    data object OnNavigateToTvShow: TvShowBookmarkIntent
    data class UndoTvShowIntent(val tvShowModel: TvShowModel): TvShowBookmarkIntent
}