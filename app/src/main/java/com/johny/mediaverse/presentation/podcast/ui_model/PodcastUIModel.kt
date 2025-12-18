package com.johny.mediaverse.presentation.podcast.ui_model

import androidx.compose.runtime.Immutable
import com.johny.mediaverse.domain.model.podcast.Podcast

@Immutable
data class PodcastUIModel(
    val podcast: Podcast,
    val isBookmark: Boolean
)
