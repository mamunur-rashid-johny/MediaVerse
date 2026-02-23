package com.johny.mediaverse.domain.model.tv_show_details

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class LastEpisodeModel(
    val name: String,
    val episodeNumber: Int,
    val seasonNumber: Int,
    val overview: String
)