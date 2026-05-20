package com.johny.mediaverse.data.model.tv_show_season

import kotlinx.serialization.Serializable

@Serializable
data class TvShowSeasonDto(
    val _id: String,
    val air_date: String?,
    val episodes: List<EpisodeDto>,
    val id: Int,
    val name: String,
    val networks: List<NetworkDto>,
    val overview: String,
    val poster_path: String?,
    val season_number: Int,
    val vote_average: Double
)