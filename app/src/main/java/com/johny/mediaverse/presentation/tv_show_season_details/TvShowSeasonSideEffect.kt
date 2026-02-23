package com.johny.mediaverse.presentation.tv_show_season_details

import com.johny.mediaverse.core.domain.utils.NetworkError

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TvShowSeasonSideEffect {
    data class OnShowErrorSideEffect(val error: NetworkError):TvShowSeasonSideEffect
}