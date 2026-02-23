package com.johny.mediaverse.data.model.tv_show_season

import kotlinx.serialization.Serializable

@Serializable
data class CrewDto(
    val adult: Boolean,
    val credit_id: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val known_for_department: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String?
)