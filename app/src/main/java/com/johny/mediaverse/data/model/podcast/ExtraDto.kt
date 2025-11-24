package com.johny.mediaverse.data.model.podcast

import kotlinx.serialization.Serializable

@Serializable
data class ExtraDto(
    val amazon_music_url: String,
    val facebook_handle: String,
    val instagram_handle: String,
    val linkedin_url: String,
    val patreon_handle: String,
    val spotify_url: String,
    val twitter_handle: String,
    val url1: String,
    val url2: String,
    val url3: String,
    val wechat_handle: String,
    val youtube_url: String
)