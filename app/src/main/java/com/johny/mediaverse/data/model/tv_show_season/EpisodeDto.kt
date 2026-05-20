package com.johny.mediaverse.data.model.tv_show_season

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    val air_date: String?,
    val crew: List<CrewDto>,
    val episode_number: Int,
    val episode_type: String,
    val guest_stars: List<GuestStarDto>,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val runtime: Int?,
    val season_number: Int,
    val show_id: Int,
    val still_path: String?,
    val vote_average: Double,
    val vote_count: Int
)