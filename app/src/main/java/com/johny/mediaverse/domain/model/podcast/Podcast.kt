package com.johny.mediaverse.domain.model.podcast

import kotlinx.serialization.Serializable

@Serializable
data class Podcast(
    val id: String,
    val publisher: String,
    val thumbnail: String,
    val title: String,
    val totalEpisodes: Int,
    val latestPubDate: Long,
    val country: String,
    val language: String,
    val type: String,
    val audioLengthInSec: Int
)
