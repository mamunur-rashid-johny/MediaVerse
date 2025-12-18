package com.johny.mediaverse.presentation.movie

import com.johny.mediaverse.domain.model.movie.MovieModel

sealed interface MovieIntent {
    data object LogoutIntent : MovieIntent
    data class SaveBookmarkIntent(val movie: MovieModel) : MovieIntent
    data class RemoveBookmarkIntent(val movieId: Int) : MovieIntent
    data class OnMovieDetailsNavigateIntent(val movieId: Int) : MovieIntent
    data object OnRetryPagination: MovieIntent

}