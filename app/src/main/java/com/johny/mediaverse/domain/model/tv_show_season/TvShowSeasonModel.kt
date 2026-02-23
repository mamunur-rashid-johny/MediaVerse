package com.johny.mediaverse.domain.model.tv_show_season

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class TvShowSeasonModel(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String?,
    val airDate: String?,
    val voteAverage: Double,
    val networks: List<NetworkModel>?,
    val episodes: List<EpisodeModel>
)
