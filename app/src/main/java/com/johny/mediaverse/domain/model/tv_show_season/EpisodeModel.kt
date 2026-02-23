package com.johny.mediaverse.domain.model.tv_show_season

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class EpisodeModel(
    val id: Int,
    val name: String,
    val overview: String,
    val episodeNumber: Int,
    val airDate: String?,
    val stillPath: String?,
    val voteAverage: Double,
    val runtime: Int? = null,
    val crew: List<CrewModel>?,
    val guestStars: List<GuestStarModel>?
)
