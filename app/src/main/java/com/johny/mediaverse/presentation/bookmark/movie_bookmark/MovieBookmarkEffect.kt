package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import com.johny.mediaverse.domain.model.movie.MovieModel

sealed interface MovieBookmarkEffect {
    data class OnNavigateToMovieDetailEffect(val movieId: Int) : MovieBookmarkEffect
    data object MovieScreenNavigationEffect: MovieBookmarkEffect
    data class ShowMessageEffect(val movieModel: MovieModel): MovieBookmarkEffect
}