package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.model.movie_details.GenreDto
import com.johny.mediaverse.data.model.movie_details.MovieDetailsDto
import com.johny.mediaverse.data.model.movie_details.ProductionCompanyDto
import com.johny.mediaverse.data.model.movie_details.ProductionCountryDto
import com.johny.mediaverse.data.model.movie_details.SpokenLanguageDto
import com.johny.mediaverse.domain.model.movie_details.GenreModel
import com.johny.mediaverse.domain.model.movie_details.MovieDetailsModel
import com.johny.mediaverse.domain.model.movie_details.ProductionCompanyModel
import com.johny.mediaverse.domain.model.movie_details.ProductionCountryModel
import com.johny.mediaverse.domain.model.movie_details.SpokenLanguageModel

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

fun MovieDetailsDto.toMovieDetails(): MovieDetailsModel {
    return MovieDetailsModel(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        budget = this.budget,
        genres = this.genres.map { it.toGenreModel() },
        homepage = this.homepage,
        id = this.id,
        originCountry = this.origin_country,
        originalLanguage = this.original_language,
        originalTitle = this.original_title,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path,
        productionCompanies = this.production_companies.map { it.toProductionCompanyModel() },
        productionCountries = this.production_countries.map { it.toProductionCountryModel() },
        releaseDate = this.release_date,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spoken_languages.map { it.toSpokenLanguageModel() },
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count
    )
}

fun GenreDto.toGenreModel(): GenreModel {
    return GenreModel(
        id = this.id,
        name = this.name
    )
}

fun ProductionCompanyDto.toProductionCompanyModel(): ProductionCompanyModel {
    return ProductionCompanyModel(
        id = this.id,
        logoPath = this.logo_path,
        name = this.name,
        originCountry = this.origin_country
    )
}

fun ProductionCountryDto.toProductionCountryModel(): ProductionCountryModel {
    return ProductionCountryModel(
        iso = this.iso_3166_1,
        name = this.name
    )
}

fun SpokenLanguageDto.toSpokenLanguageModel(): SpokenLanguageModel {
    return SpokenLanguageModel(
        englishName = this.english_name,
        iso = this.iso_639_1,
        name = this.name
    )
}