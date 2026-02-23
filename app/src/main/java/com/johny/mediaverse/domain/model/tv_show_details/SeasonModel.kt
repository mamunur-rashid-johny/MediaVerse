package com.johny.mediaverse.domain.model.tv_show_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class SeasonModel(
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterPath: String?,
    val seasonNumber: Int
)
