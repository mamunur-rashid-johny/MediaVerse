package com.johny.mediaverse.presentation.movie

sealed interface MovieSideEffect {
    data object NavigateToOnboarding : MovieSideEffect
    data class NavigateToDetails(val movieId: Int) : MovieSideEffect
    data object RetryPaginationEffect: MovieSideEffect
}