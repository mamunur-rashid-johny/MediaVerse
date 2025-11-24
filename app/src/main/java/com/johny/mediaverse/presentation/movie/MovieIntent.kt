package com.johny.mediaverse.presentation.movie

sealed interface MovieIntent {
    data object LogoutIntent : MovieIntent
}