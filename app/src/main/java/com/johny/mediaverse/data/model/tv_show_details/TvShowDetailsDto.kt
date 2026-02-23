package com.johny.mediaverse.data.model.tv_show_details

import kotlinx.serialization.Serializable

@Serializable
data class TvShowDetailsDto(
    val adult: Boolean,
    val backdrop_path: String,
    val created_by: List<CreatedByDto>,
    val episode_run_time: List<Int>,
    val first_air_date: String,
    val genres: List<GenreDto>,
    val homepage: String,
    val id: Int,
    val in_production: Boolean,
    val languages: List<String>,
    val last_air_date: String,
    val last_episode_to_air: LastEpisodeToAirDto,
    val name: String,
    val networks: List<NetworkDto>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanyDto>,
    val production_countries: List<ProductionCountryDto>,
    val seasons: List<SeasonDto>,
    val spoken_languages: List<SpokenLanguageDto>,
    val status: String,
    val tagline: String,
    val type: String,
    val vote_average: Double,
    val vote_count: Int
)