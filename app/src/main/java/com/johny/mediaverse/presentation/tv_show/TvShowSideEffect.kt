package com.johny.mediaverse.presentation.tv_show

import com.johny.mediaverse.presentation.movie.MovieSideEffect

sealed interface TvShowSideEffect {
    data class NavigateToDetailsEffect(val tvShowId: Int) : TvShowSideEffect
    data class ShowErrorEffect(val message: String, val actionLabel: String) : TvShowSideEffect
}