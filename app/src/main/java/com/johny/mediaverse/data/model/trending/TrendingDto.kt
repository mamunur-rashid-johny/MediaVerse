package com.johny.mediaverse.data.model.trending

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
@Serializable
data class TrendingDto(
    val adult: Boolean = false,
    val backdrop_path: String? = null,
    val id: Int,
    // Movies expose `title`/`original_title`/`release_date`/`video`,
    // TV shows expose `name`/`original_name`/`first_air_date` instead.
    val title: String? = null,
    val name: String? = null,
    val original_language: String = "",
    val original_title: String? = null,
    val original_name: String? = null,
    val overview: String = "",
    val media_type: String = "",
    val genre_ids: List<Int> = emptyList(),
    val popularity: Double = 0.0,
    val release_date: String? = null,
    val first_air_date: String? = null,
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)
