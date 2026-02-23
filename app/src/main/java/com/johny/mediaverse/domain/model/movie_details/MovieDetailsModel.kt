package com.johny.mediaverse.domain.model.movie_details

import kotlinx.serialization.Serializable

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@Serializable
data class MovieDetailsModel(
    val adult: Boolean,
    val backdropPath: String?,
    val budget: Int,
    val genres: List<GenreModel>,
    val homepage: String,
    val id: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyModel>,
    val productionCountries: List<ProductionCountryModel>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageModel>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)
