package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.data.model.tv_show.TvShowResponseDto
import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel

/**
 * Created by Johny on 25/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface SimilarTvShowApi {
    suspend fun getTvShowDetails(tvShowId: Int): Result<TvShowDetailsModel, NetworkError>
    suspend fun getPagedTvShow(tvShowId: Int, page: Int): Result<TvShowResponseDto, NetworkError>
}