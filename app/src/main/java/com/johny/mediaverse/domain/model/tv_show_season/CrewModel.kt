package com.johny.mediaverse.domain.model.tv_show_season

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class CrewModel(
    val job: String,
    val name: String
)
