package com.johny.mediaverse.presentation.tv_show_season_details

import com.johny.mediaverse.domain.model.tv_show_season.TvShowSeasonModel

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
data class TvShowSeasonState(
    val seasonDetails: TvShowSeasonModel? = null,
    val isLoading: Boolean = false
)
