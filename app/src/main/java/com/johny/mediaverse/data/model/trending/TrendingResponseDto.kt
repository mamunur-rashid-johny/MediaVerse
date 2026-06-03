package com.johny.mediaverse.data.model.trending

import kotlinx.serialization.Serializable


/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
@Serializable
data class TrendingResponseDto(
    val page: Int,
    val results: List<TrendingDto>,
    val total_pages: Int,
    val total_results: Int
)
