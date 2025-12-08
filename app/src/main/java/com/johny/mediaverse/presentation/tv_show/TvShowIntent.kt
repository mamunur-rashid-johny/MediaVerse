package com.johny.mediaverse.presentation.tv_show

import com.johny.mediaverse.domain.model.tv_show.TvShowModel

sealed interface TvShowIntent {
    data class NavigateToDetailsIntent(val tvShowId: Int): TvShowIntent
    data class SaveBookmarkIntent(val tvShowModel: TvShowModel): TvShowIntent
    data class RemoveBookmarkIntent(val tvShowId: Int): TvShowIntent
    data class ShowErrorIntent(val message: String, val actionLabel: String): TvShowIntent
}