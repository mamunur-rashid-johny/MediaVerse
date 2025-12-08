package com.johny.mediaverse.data.model.tv_show

import kotlinx.serialization.Serializable

@Serializable
data class TvShowResponseDto(
    val page: Int,
    val results: List<ResultDto>,
    val total_pages: Int,
    val total_results: Int
)