package com.johny.mediaverse.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.mapper.toTrendingModel
import com.johny.mediaverse.domain.model.trending.TrendingModel
import com.johny.mediaverse.domain.paging_source.GenericPagingSource
import com.johny.mediaverse.domain.repository.TrendingApi
import com.johny.mediaverse.domain.repository.TrendingRepository
import kotlinx.coroutines.flow.Flow

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TrendingRepositoryImpl(
    private val api: TrendingApi,
    private val context: Context
) : TrendingRepository {
    override fun getTrending(): Flow<PagingData<TrendingModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                GenericPagingSource(
                    context = context,
                    fetch = { page -> api.getPagedTrending(page) },
                    itemsOf = { it.results },
                    hasNextPage = { response, page -> page < response.total_pages },
                    mapper = { it.toTrendingModel() },
                    idSelector = { it.id },
                )
            }
        ).flow
    }

}