package com.johny.mediaverse.domain.model.podcast_details

data class PodcastDetails(
    val id: String,
    val title: String,
    val publisher: String,
    val thumbnail: String,
    val totalEpisodes: Int,
    val description: String,
    val language: String,
    val country: String,
    val website: String?,
    val nextEpisodePubDate: Long?,
    val episodes: List<EpisodeModel>
)
