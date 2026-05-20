package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class CreatedByDto(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String?
)