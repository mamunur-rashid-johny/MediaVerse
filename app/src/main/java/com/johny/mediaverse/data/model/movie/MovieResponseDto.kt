package com.johny.mediaverse.data.model.movie

import kotlinx.serialization.Serializable


@Serializable
data class MovieResponseDto(
    val page: Int,
    val results: List<ResultDto>,
    val total_pages: Int,
    val total_results: Int
)