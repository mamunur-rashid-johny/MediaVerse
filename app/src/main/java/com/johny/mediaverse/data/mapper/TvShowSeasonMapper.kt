package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.model.tv_show_season.CrewDto
import com.johny.mediaverse.data.model.tv_show_season.EpisodeDto
import com.johny.mediaverse.data.model.tv_show_season.GuestStarDto
import com.johny.mediaverse.data.model.tv_show_season.NetworkDto
import com.johny.mediaverse.data.model.tv_show_season.TvShowSeasonDto
import com.johny.mediaverse.domain.model.tv_show_season.CrewModel
import com.johny.mediaverse.domain.model.tv_show_season.EpisodeModel
import com.johny.mediaverse.domain.model.tv_show_season.GuestStarModel
import com.johny.mediaverse.domain.model.tv_show_season.NetworkModel
import com.johny.mediaverse.domain.model.tv_show_season.TvShowSeasonModel

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

fun TvShowSeasonDto.toTvShowSeasonModel(): TvShowSeasonModel {
    return TvShowSeasonModel(
        id = this.id,
        name = this.name,
        overview = this.overview,
        posterPath = this.poster_path,
        airDate = this.air_date,
        voteAverage = this.vote_average,
        networks = this.networks.map { it.toNetworkModel() },
        episodes = this.episodes.map { it.toEpisodeModel() }
    )
}

fun NetworkDto.toNetworkModel(): NetworkModel {
    return NetworkModel(
        name = this.name,
        logoPath = this.logo_path
    )
}

fun EpisodeDto.toEpisodeModel(): EpisodeModel {
    return EpisodeModel(
        id = this.id,
        name = this.name,
        overview = this.overview,
        episodeNumber = this.episode_number,
        airDate = this.air_date,
        stillPath = this.still_path,
        voteAverage = this.vote_average,
        runtime = this.runtime,
        crew = this.crew.map { it.toCrewModel() },
        guestStars = this.guest_stars.map { it.toGuestStarModel() }
    )
}

fun GuestStarDto.toGuestStarModel(): GuestStarModel {
    return GuestStarModel(
        character = this.character,
        name = this.name,
        profilePath = this.profile_path
    )
}

fun CrewDto.toCrewModel(): CrewModel {
    return CrewModel(
        job = this.job,
        name = this.name
    )
}