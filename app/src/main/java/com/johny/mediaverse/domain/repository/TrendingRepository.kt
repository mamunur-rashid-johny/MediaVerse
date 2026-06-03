package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.domain.model.trending.TrendingModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface TrendingRepository {
    fun getTrending(): Flow<PagingData<TrendingModel>>
}