package com.johny.mediaverse.presentation.movie

sealed interface MovieSideEffect {
    data object NavigateToOnboarding : MovieSideEffect
}