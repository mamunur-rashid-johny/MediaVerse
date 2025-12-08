package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.data.model.tv_show.TvShowResponseDto
import com.johny.mediaverse.core.domain.utils.Result

interface TvShowApi {
    suspend fun getPagedTvShow(page: Int): Result<TvShowResponseDto, NetworkError>
}