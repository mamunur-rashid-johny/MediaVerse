package com.johny.mediaverse.domain.model.podcast

data class PodcastResponse(
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val nextPageNumber: Int,
    val pageNumber: Int,
    val previousPageNumber: Int,
    val total: Int,
    val podcasts: List<Podcast>
)
