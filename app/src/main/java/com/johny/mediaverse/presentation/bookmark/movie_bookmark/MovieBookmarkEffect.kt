package com.johny.mediaverse.presentation.bookmark.movie_bookmark

sealed interface MovieBookmarkEffect {
    data class OnNavigateToMovieDetailEffect(val movieId: Int) : MovieBookmarkEffect
    data object MovieScreenNavigationEffect: MovieBookmarkEffect
}