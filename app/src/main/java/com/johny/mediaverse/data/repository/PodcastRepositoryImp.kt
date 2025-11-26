package com.johny.mediaverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.mapper.toPodcastEntity
import com.johny.mediaverse.data.paging_source.PodcastPagingSource
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.ListenNoteApi
import com.johny.mediaverse.domain.repository.PodcastRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class PodcastRepositoryImp(
    private val api: ListenNoteApi,
    private val dao: PodcastDao
) : PodcastRepository {
    override fun getBestPodcasts(): Flow<PagingData<Podcast>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                PodcastPagingSource(api)
            }
        ).flow
    }

    override fun getSavedPodcastIds(): Flow<Set<String>> {
        return dao.getPodcast()
            .map { list -> list.toSet() }
    }

    override suspend fun saveBookmark(podcast: Podcast) {
        dao.savePodcast(podcast.toPodcastEntity())
    }

    override suspend fun removeBookmark(podcastId: String) {
        dao.removePodcast(podcastId)
    }
}