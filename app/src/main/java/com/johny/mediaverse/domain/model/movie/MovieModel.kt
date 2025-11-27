package com.johny.mediaverse.domain.model.movie

import com.johny.mediaverse.utils.DateFormat
import com.johny.mediaverse.utils.toFormattedDate
import kotlinx.serialization.Serializable

@Serializable
data class MovieModel(
    val id: Int,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterPath: String
){
    val year: String
        get() = releaseDate.toFormattedDate(DateFormat.YYYY)
}
