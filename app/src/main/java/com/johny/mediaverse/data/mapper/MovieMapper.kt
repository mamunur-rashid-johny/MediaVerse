package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.model.movie.ResultDto
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.presentation.movie.model.MovieModelUi

fun ResultDto.toMovieModel(): MovieModel {
    return MovieModel(
        id = this.id,
        title = this.title,
        rating = this.vote_average,
        releaseDate = this.release_date,
        posterPath = this.poster_path ?: ""
    )
}

fun MovieModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath,
    )
}

fun MovieEntity.toMovieModel(): MovieModel {
    return MovieModel(
        id = this.id,
        title = this.title,
        rating = this.rating,
        releaseDate = this.releaseDate,
        posterPath = this.posterPath
    )
}

fun MovieEntity.toMovieUIModel(): MovieModelUi {
    return MovieModelUi(
        movie = this.toMovieModel(),
        isBookmark = true
    )
}