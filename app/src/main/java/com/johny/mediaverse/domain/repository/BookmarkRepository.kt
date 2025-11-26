package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getPagedPodcast(): Flow<PagingData<PodcastEntity>>
    suspend fun removeBookmark(podcastId: String)
}