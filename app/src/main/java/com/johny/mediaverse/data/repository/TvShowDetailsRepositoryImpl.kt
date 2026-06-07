package com.johny.mediaverse.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.data.local.dao.TvShowDao
import com.johny.mediaverse.data.mapper.toTvShowEntity
import com.johny.mediaverse.data.mapper.toTvShowModel
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel
import com.johny.mediaverse.domain.paging_source.GenericPagingSource
import com.johny.mediaverse.domain.repository.SimilarTvShowApi
import com.johny.mediaverse.domain.repository.TvShowDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TvShowDetailsRepositoryImpl(
    private val api: SimilarTvShowApi,
    private val context: Context,
    private val dao: TvShowDao
) : TvShowDetailsRepository {
    override suspend fun getTvShowDetails(tvShowId: Int): Result<TvShowDetailsModel, NetworkError> {
        return api.getTvShowDetails(tvShowId)
    }

    override fun getSimilarTvShows(tvShowId: Int): Flow<PagingData<TvShowModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    context = context,
                    fetch = { page -> api.getPagedTvShow(tvShowId, page) },
                    itemsOf = { it.results },
                    hasNextPage = { response, page -> page < response.total_pages },
                    mapper = { it.toTvShowModel() },
                    idSelector = { it.id },
                )
            }
        ).flow
    }

    override fun getSavedTvShowIds(): Flow<Set<Int>> {
        return dao.getTvShowId()
            .map { it.toSet() }
    }

    override suspend fun saveBookmark(tvShow: TvShowModel) {
        dao.saveTvShow(tvShow.toTvShowEntity())
    }

    override suspend fun removeBookmark(tvShowId: Int) {
        dao.removeTvShow(tvShowId)
    }
}