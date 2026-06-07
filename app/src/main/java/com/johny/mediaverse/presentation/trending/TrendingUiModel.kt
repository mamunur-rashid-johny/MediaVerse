package com.johny.mediaverse.presentation.trending

import com.johny.mediaverse.domain.model.trending.TrendingModel

/**
 * Created by Johny on 7/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
data class TrendingUiModel(
    val trending: TrendingModel,
    val isBookmarked: Boolean
)
