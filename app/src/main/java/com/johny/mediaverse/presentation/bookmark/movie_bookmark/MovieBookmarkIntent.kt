package com.johny.mediaverse.presentation.bookmark.movie_bookmark

sealed interface MovieBookmarkIntent {
    data class OnMovieBookmarkClickIntent(val movieId: Int) : MovieBookmarkIntent
    data class OnMovieBookRemoveIntent(val movieId: Int) : MovieBookmarkIntent
    data object OnNavigateToMovie: MovieBookmarkIntent
}