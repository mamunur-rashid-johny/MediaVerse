package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import com.johny.mediaverse.domain.model.movie.MovieModel

sealed interface MovieBookmarkIntent {
    data class OnMovieBookmarkClickIntent(val movieId: Int) : MovieBookmarkIntent
    data class OnMovieBookRemoveIntent(val movieModel: MovieModel) : MovieBookmarkIntent
    data class ONMovieBookmarkUndoIntent(val movieModel: MovieModel): MovieBookmarkIntent
    data object OnNavigateToMovie: MovieBookmarkIntent
}