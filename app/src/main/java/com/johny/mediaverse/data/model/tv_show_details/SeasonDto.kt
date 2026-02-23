package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class SeasonDto(
    val air_date: String?,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String?,
    val season_number: Int,
    val vote_average: Double
)