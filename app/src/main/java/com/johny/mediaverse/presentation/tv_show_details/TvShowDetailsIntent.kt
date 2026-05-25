package com.johny.mediaverse.presentation.tv_show_details

import com.johny.mediaverse.domain.model.tv_show.TvShowModel

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TvShowDetailsIntent {
    data class OnNavigateToSeriesDetails(val seriesId: Int, val seasonNumber: Int) : TvShowDetailsIntent
    data class NavigateToDetailsIntent(val tvShowId: Int) : TvShowDetailsIntent
    data class SaveBookmarkIntent(val tvShowModel: TvShowModel) : TvShowDetailsIntent
    data class RemoveBookmarkIntent(val tvShowId: Int) : TvShowDetailsIntent
}