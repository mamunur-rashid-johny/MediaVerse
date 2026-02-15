package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.domain.model.movie_details.MovieDetailsModel

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsModel, NetworkError>
}