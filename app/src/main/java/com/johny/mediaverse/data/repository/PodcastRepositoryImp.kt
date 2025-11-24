package com.johny.mediaverse.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.paging_source.PodcastPagingSource
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.ListenNoteApi
import com.johny.mediaverse.domain.repository.PodcastRepository
import kotlinx.coroutines.flow.Flow

class PodcastRepositoryImp(
    private val api: ListenNoteApi
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
}