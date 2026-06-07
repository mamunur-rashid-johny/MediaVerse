package com.johny.mediaverse.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.MovieDao
import com.johny.mediaverse.data.local.dao.TvShowDao
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.data.mapper.toTrendingModel
import com.johny.mediaverse.data.mapper.toTvShowEntity
import com.johny.mediaverse.domain.model.trending.TrendingModel
import com.johny.mediaverse.domain.paging_source.GenericPagingSource
import com.johny.mediaverse.domain.repository.TrendingApi
import com.johny.mediaverse.domain.repository.TrendingRepository
import com.johny.mediaverse.utils.MediaTypeEnum.MOVIE
import com.johny.mediaverse.utils.MediaTypeEnum.TV
import com.johny.mediaverse.utils.toMediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TrendingRepositoryImpl(
    private val api: TrendingApi,
    private val tvDao: TvShowDao,
    private val movieDao: MovieDao,
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

    override suspend fun saveBookmark(trending: TrendingModel) {
        when (trending.mediaType.toMediaType()) {
            MOVIE -> {
                movieDao.saveMovie(trending.toMovieEntity())
            }

            TV -> {
                tvDao.saveTvShow(trending.toTvShowEntity())
            }

            else -> {}
        }
    }

    override suspend fun removeBookmark(trending: TrendingModel) {
        when (trending.mediaType.toMediaType()) {
            MOVIE -> {
                movieDao.removeMovie(trending.id)
            }

            TV -> {
                tvDao.removeTvShow(trending.id)
            }

            else -> {}
        }
    }

    override fun getSavedMovies(): Flow<Set<Int>> {
        return movieDao.getMoviesId().map { it.toSet() }
    }

    override fun getSavedTvShows(): Flow<Set<Int>> {
        return tvDao.getTvShowId().map { it.toSet() }
    }
}