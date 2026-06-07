package com.johny.mediaverse.presentation.trending

import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.model.trending.TrendingModel

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TrendingIntent {
    data class OnTrendingItemIntent(val trendingModel:TrendingModel):TrendingIntent
    data class SaveBookmarkIntent(val trending: TrendingModel):TrendingIntent
    data class RemoveBookmarkIntent(val trending: TrendingModel): TrendingIntent
    data object RetryPaginationIntent: TrendingIntent
}