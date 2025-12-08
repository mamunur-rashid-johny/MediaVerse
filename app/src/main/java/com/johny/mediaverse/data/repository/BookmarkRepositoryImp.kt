package com.johny.mediaverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.MovieDao
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.local.dao.TvShowDao
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import com.johny.mediaverse.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImp(
    private val podcastDao: PodcastDao,
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) : BookmarkRepository {
    override fun getPagedPodcast(): Flow<PagingData<PodcastEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                podcastDao.getPagedPodcast()
            },
        ).flow
    }

    override suspend fun removePodcastBookmark(podcastId: String) {
        podcastDao.removePodcast(podcastId)
    }

    override fun getPagedMovie(): Flow<PagingData<MovieEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                movieDao.getPagedMovie()
            },
        ).flow
    }

    override suspend fun removeMovieBookmark(movieId: Int) {
        movieDao.removeMovie(movieId = movieId)
    }

    override fun getPagedTvShow(): Flow<PagingData<TvShowEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                tvShowDao.getPagedTvShow()
            }
        ).flow
    }

    override suspend fun removeTvShowBookmark(tvShowId: Int) {
        tvShowDao.removeTvShow(tvShowId)
    }
}