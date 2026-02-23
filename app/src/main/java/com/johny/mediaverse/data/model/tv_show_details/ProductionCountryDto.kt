package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountryDto(
    val iso_3166_1: String,
    val name: String
)