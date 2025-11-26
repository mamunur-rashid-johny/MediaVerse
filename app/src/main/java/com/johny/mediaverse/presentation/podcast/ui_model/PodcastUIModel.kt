package com.johny.mediaverse.presentation.podcast.ui_model

import com.johny.mediaverse.domain.model.podcast.Podcast

data class PodcastUIModel(
    val podcast: Podcast,
    val isBookmark: Boolean
)
