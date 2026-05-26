package com.johny.mediaverse.presentation.movie_details

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.domain.model.movie_details.MovieDetailsModel

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
data class MovieDetailsState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetailsModel? = null,
    val error: NetworkError? = null
)
