package com.johny.mediaverse.domain.model.tv_show_details


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
@Serializable
data class TvShowDetailsModel(
    val id: Int,
    val name: String,
    val overview: String,
    val backdropPath: String?,
    val genres: List<GenreModel>,
    val lastEpisode: LastEpisodeModel,
    val seasons: List<SeasonModel>,
    val tagline: String,
    val networks: List<NetworkModel>
)
