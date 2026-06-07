package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import com.johny.mediaverse.data.model.trending.TrendingDto
import com.johny.mediaverse.domain.model.trending.TrendingModel

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

fun TrendingDto.toTrendingModel(): TrendingModel {
    return TrendingModel(
        adult = this.adult,
        backdropPath = this.backdrop_path,
        id = this.id,
        title = this.title ?: this.name.orEmpty(),
        originalLanguage = this.original_language,
        originalTitle = this.original_title ?: this.original_name.orEmpty(),
        overview = this.overview,
        mediaType = this.media_type,
        genreIds = this.genre_ids,
        popularity = this.popularity,
        releaseDate = this.release_date ?: this.first_air_date.orEmpty(),
        video = this.video,
        voteAverage = this.vote_average,
        voteCount = this.vote_count
    )
}

fun TrendingModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        rating = this.voteAverage,
        releaseDate = this.releaseDate,
        posterPath = this.backdropPath ?: ""
    )
}

fun TrendingModel.toTvShowEntity(): TvShowEntity {
    return TvShowEntity(
        id = this.id,
        title = this.title,
        rating = this.voteAverage,
        releaseDate = this.releaseDate,
        posterPath = this.backdropPath ?: ""
    )
}