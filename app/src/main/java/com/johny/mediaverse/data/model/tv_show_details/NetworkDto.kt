package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class NetworkDto(
    val id: Int,
    val logo_path: String,
    val name: String,
    val origin_country: String
)