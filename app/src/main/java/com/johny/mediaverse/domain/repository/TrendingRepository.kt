package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.domain.model.trending.TrendingModel
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface TrendingRepository {
    fun getTrending(): Flow<PagingData<TrendingModel>>
    suspend fun saveBookmark(trending: TrendingModel)
    suspend fun removeBookmark(trending: TrendingModel)
    fun getSavedMovies(): Flow<Set<Int>>
    fun getSavedTvShows(): Flow<Set<Int>>
}