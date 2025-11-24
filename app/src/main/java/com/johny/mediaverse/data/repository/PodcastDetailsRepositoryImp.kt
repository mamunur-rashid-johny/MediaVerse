package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.domain.utils.map
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.mapper.toPodcastDetails
import com.johny.mediaverse.data.model.podcast_details.PodcastDetailsDto
import com.johny.mediaverse.domain.model.podcast_details.PodcastDetails
import com.johny.mediaverse.domain.repository.PodcastDetailRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class PodcastDetailsRepositoryImp(
    private val httpClient: HttpClient
): PodcastDetailRepository {
    override suspend fun getPodcastDetails(podcastId: String): Result<PodcastDetails, NetworkError> {
        return safeCall<PodcastDetailsDto> {
            httpClient.get(
                urlString = "${Constants.ListenNoteUrls.LISTEN_NOTE_BASE_URL}${Constants.ListenNoteUrls.PODCAST_DETAILS}$podcastId"
            ) {
                headers.append("App","MediaVerse")
                headers.append("X-ListenAPI-Key", BuildConfig.LISTEN_NOTE_API_KEY)
            }
        }.map { response ->
            response.toPodcastDetails()
        }
    }
}