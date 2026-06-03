package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.data.model.trending.TrendingResponseDto
import com.johny.mediaverse.core.domain.utils.Result



/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface TrendingApi {
    suspend fun getPagedTrending(page: Int):Result<TrendingResponseDto, NetworkError>
}