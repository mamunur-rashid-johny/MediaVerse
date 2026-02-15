package com.johny.mediaverse.domain.model.movie_details

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class ProductionCompanyModel(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String
)
