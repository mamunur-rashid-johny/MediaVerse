package com.johny.mediaverse.presentation.tv_show.model

import com.johny.mediaverse.domain.model.tv_show.TvShowModel

data class TvShowUiModel(
    val tvShow: TvShowModel,
    val isBookmarked: Boolean
)
