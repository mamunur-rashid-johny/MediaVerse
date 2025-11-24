package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.domain.model.podcast_details.PodcastDetails

interface PodcastDetailRepository {
    suspend fun getPodcastDetails(podcastId: String): Result<PodcastDetails, NetworkError>

}