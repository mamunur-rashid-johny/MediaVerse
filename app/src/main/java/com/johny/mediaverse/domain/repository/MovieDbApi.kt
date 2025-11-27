package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.data.model.movie.MovieResponseDto

interface MovieDbApi {
    suspend fun getMoviePaged(page: Int): Result<MovieResponseDto, NetworkError>
}