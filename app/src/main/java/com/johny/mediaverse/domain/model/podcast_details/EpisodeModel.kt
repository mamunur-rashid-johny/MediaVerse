package com.johny.mediaverse.domain.model.podcast_details

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeModel(
    val id: String,
    val title: String,
    val description: String,
    val audio: String,
    val thumbnail: String,
    val pubDate: Long,
    val audioLengthSec: Int
)
