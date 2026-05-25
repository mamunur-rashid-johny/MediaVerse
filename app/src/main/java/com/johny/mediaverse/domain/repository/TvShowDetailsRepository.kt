package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface TvShowDetailsRepository {
    suspend fun getTvShowDetails(tvShowId: Int): Result<TvShowDetailsModel, NetworkError>
    fun getSimilarTvShows(tvShowId: Int): Flow<PagingData<TvShowModel>>
    fun getSavedTvShowIds(): Flow<Set<Int>>
    suspend fun saveBookmark(tvShow: TvShowModel)
    suspend fun removeBookmark(tvShowId: Int)
}