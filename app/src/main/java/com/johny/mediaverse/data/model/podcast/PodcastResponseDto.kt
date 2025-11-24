package com.johny.mediaverse.data.model.podcast

import kotlinx.serialization.Serializable

@Serializable
data class PodcastResponseDto(
    val has_next: Boolean,
    val has_previous: Boolean,
    val id: Int,
    val listennotes_url: String,
    val name: String,
    val next_page_number: Int,
    val page_number: Int,
    val parent_id: Int?,
    val podcasts: List<PodcastDto>,
    val previous_page_number: Int,
    val total: Int
)