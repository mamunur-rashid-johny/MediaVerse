package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

import com.johny.mediaverse.domain.model.tv_show.TvShowModel

sealed interface TvShowBookmarkEffect {
    data class OnNavigateToTvShowDetailEffect(val tvShowId: Int) : TvShowBookmarkEffect
    data object TvShowScreenNavigationEffect: TvShowBookmarkEffect
    data class ShowMessageEffect(val tvShowModel: TvShowModel): TvShowBookmarkEffect
}