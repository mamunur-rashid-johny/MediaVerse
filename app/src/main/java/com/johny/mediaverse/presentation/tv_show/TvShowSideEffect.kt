package com.johny.mediaverse.presentation.tv_show

sealed interface TvShowSideEffect {
    data class NavigateToDetailsEffect(val tvShowId: Int) : TvShowSideEffect
    data object RetryPaginationEffect: TvShowSideEffect
}