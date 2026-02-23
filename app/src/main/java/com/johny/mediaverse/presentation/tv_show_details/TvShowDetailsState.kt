package com.johny.mediaverse.presentation.tv_show_details

import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
data class TvShowDetailsState(
    val tvShowDetails: TvShowDetailsModel? = null,
    val isLoading: Boolean = false
)