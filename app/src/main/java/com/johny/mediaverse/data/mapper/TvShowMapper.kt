package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import com.johny.mediaverse.data.model.tv_show.ResultDto
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel

fun ResultDto.toTvShowModel(): TvShowModel{
    return TvShowModel(
        id = this.id,
        title = this.name,
        rating = this.vote_average,
        releaseDate = this.first_air_date,
        posterPath = this.poster_path
    )
}

fun TvShowModel.toMovieEntity(): TvShowEntity {
    return TvShowEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
    )
}

fun TvShowEntity.toTvShowModel(): TvShowModel {
    return TvShowModel(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath
    )
}

fun TvShowEntity.toTvShowUIModel(): TvShowUiModel {
    return TvShowUiModel(
        tvShow = this.toTvShowModel(),
        isBookmarked = true
    )
}

