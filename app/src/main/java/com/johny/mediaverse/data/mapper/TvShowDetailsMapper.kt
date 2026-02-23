package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.model.tv_show_details.GenreDto
import com.johny.mediaverse.data.model.tv_show_details.LastEpisodeToAirDto
import com.johny.mediaverse.data.model.tv_show_details.NetworkDto
import com.johny.mediaverse.data.model.tv_show_details.SeasonDto
import com.johny.mediaverse.data.model.tv_show_details.TvShowDetailsDto
import com.johny.mediaverse.domain.model.tv_show_details.GenreModel
import com.johny.mediaverse.domain.model.tv_show_details.LastEpisodeModel
import com.johny.mediaverse.domain.model.tv_show_details.NetworkModel
import com.johny.mediaverse.domain.model.tv_show_details.SeasonModel
import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

fun TvShowDetailsDto.toTvShowDetailsModel(): TvShowDetailsModel{
    return TvShowDetailsModel(
        id = this.id,
        name = this.name,
        overview = this.overview,
        backdropPath = this.backdrop_path,
        genres = this.genres.map { it.toGenreModel() },
        lastEpisode = this.last_episode_to_air.toEpisodeModel(),
        seasons = this.seasons.map { it.toSeasonModel() },
        tagline = this.tagline,
        networks = this.networks.map { it.toNetworkModel() }
    )
}

fun SeasonDto.toSeasonModel(): SeasonModel{
    return SeasonModel(
        name = this.name,
        episodeCount = this.episode_count,
        airDate = this.air_date,
        posterPath = this.poster_path,
        seasonNumber = this.season_number
    )
}

fun LastEpisodeToAirDto.toEpisodeModel(): LastEpisodeModel{
    return LastEpisodeModel(
        name = this.name,
        episodeNumber = this.episode_number,
        seasonNumber = this.season_number,
        overview = this.overview
    )
}

fun GenreDto.toGenreModel(): GenreModel{
    return GenreModel(
        name = this.name
    )
}

fun NetworkDto.toNetworkModel(): NetworkModel{
    return NetworkModel(
        name = this.name
    )
}