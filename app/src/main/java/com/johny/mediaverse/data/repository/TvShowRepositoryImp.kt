package com.johny.mediaverse.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.TvShowDao
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.paging_source.TvShowPagingSource
import com.johny.mediaverse.domain.repository.TvShowApi
import com.johny.mediaverse.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TvShowRepositoryImp(
    private val api: TvShowApi,
    private val dao: TvShowDao,
    private val context: Context
) : TvShowRepository {
    override fun getMovies(): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                TvShowPagingSource(api, context)
            }
        ).flow
    }

    override fun getSavedTvShowIds(): Flow<Set<Int>> {
        return dao.getTvShowId()
            .map { it.toSet() }
    }

    override suspend fun saveBookmark(tvShow: TvShowModel) {
        dao.saveTvShow(tvShow.toMovieEntity())
    }

    override suspend fun removeBookmark(tvShowId: Int) {
        dao.removeTvShow(tvShowId)
    }

}