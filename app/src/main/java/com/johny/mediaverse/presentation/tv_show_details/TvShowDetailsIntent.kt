package com.johny.mediaverse.presentation.tv_show_details

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TvShowDetailsIntent {
    data class OnNavigateToSeriesDetails(val seriesId: Int, val seasonNumber: Int): TvShowDetailsIntent
}