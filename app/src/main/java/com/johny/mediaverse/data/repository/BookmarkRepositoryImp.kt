package com.johny.mediaverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow

class BookmarkRepositoryImp(
    private val dao: PodcastDao
) : BookmarkRepository {
    override fun getPagedPodcast(): Flow<PagingData<PodcastEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                initialLoadSize = 20
            ),
            pagingSourceFactory = {
                dao.getPagedPodcast()
            },
        ).flow
    }

    override suspend fun removeBookmark(podcastId: String) {
        dao.removePodcast(podcastId)
    }
}