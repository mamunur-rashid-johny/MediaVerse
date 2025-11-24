package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.model.podcast_details.PodcastDetails
import kotlinx.coroutines.flow.Flow

interface PodcastRepository {
    fun getBestPodcasts(): Flow<PagingData<Podcast>>
}