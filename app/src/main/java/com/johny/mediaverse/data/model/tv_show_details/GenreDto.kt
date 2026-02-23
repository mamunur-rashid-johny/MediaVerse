package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
)