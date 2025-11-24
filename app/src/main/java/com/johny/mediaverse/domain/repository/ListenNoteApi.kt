package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.data.model.podcast.PodcastResponseDto
import com.johny.mediaverse.core.domain.utils.Result

interface ListenNoteApi {
    suspend fun getPodcastsPaged(page: Int): Result<PodcastResponseDto, NetworkError>
}