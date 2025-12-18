package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getPagedPodcast(): Flow<PagingData<PodcastEntity>>
    suspend fun removePodcastBookmark(podcastId: String)
    suspend fun undoPodcast(podcastEntity: PodcastEntity)
    fun getPagedMovie(): Flow<PagingData<MovieEntity>>
    suspend fun removeMovieBookmark(movieId: Int)
    suspend fun undoMovie(movieEntity: MovieEntity)
    fun getPagedTvShow(): Flow<PagingData<TvShowEntity>>
    suspend fun removeTvShowBookmark(tvShowId: Int)
    suspend fun undoTvShow(tvShowEntity: TvShowEntity)
}