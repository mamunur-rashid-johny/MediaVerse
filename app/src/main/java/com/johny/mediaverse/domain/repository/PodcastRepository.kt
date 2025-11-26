package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {
    fun getBestPodcasts(): Flow<PagingData<Podcast>>
    fun getSavedPodcastIds(): Flow<Set<String>>
    suspend fun saveBookmark(podcast: Podcast)
    suspend fun removeBookmark(podcastId: String)
}