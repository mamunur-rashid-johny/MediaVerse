package com.johny.mediaverse.presentation.tv_show

import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.presentation.movie.MovieIntent

sealed interface TvShowIntent {
    data class NavigateToDetailsIntent(val tvShowId: Int): TvShowIntent
    data class SaveBookmarkIntent(val tvShowModel: TvShowModel): TvShowIntent
    data class RemoveBookmarkIntent(val tvShowId: Int): TvShowIntent
    data object OnRetryPagination: TvShowIntent
}