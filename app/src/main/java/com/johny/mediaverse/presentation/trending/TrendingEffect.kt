package com.johny.mediaverse.presentation.trending

import com.johny.mediaverse.utils.MediaTypeEnum

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TrendingEffect {
    data class NavigateToDetails(val id: Int,val mediaTypeEnum: MediaTypeEnum) : TrendingEffect
    data object RetryPagination : TrendingEffect
}