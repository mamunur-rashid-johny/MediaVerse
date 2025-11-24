package com.johny.mediaverse.data.model.podcast_details

import kotlinx.serialization.Serializable

@Serializable
data class LookingForDto(
    val cohosts: Boolean,
    val cross_promotion: Boolean,
    val guests: Boolean,
    val sponsors: Boolean
)