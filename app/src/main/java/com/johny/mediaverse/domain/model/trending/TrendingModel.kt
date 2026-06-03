package com.johny.mediaverse.domain.model.trending

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
data class TrendingModel(
    val adult: Boolean,
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val mediaType: String,
    val genreIds: List<Int>,
    val popularity: Double,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
