package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    fun getMovies(): Flow<PagingData<TvShowModel>>
    fun getSavedTvShowIds(): Flow<Set<Int>>
    suspend fun saveBookmark(tvShow: TvShowModel)
    suspend fun removeBookmark(tvShowId: Int)
}