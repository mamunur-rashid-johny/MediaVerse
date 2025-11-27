package com.johny.mediaverse.presentation.movie.model

import com.johny.mediaverse.domain.model.movie.MovieModel

data class MovieModelUi(
    val movie: MovieModel,
    val isBookmark: Boolean
)
