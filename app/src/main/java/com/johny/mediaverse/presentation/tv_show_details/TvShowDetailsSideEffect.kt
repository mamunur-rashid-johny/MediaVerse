package com.johny.mediaverse.presentation.tv_show_details

import androidx.annotation.StringRes
import com.johny.mediaverse.core.domain.utils.NetworkError

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
sealed interface TvShowDetailsSideEffect {
    data class OnNavigateSideEffect(val seriesId: Int, val seasonNumber: Int):TvShowDetailsSideEffect
    data class ShowErrorMessage(val message: NetworkError): TvShowDetailsSideEffect
}