package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.model.podcast.PodcastResponseDto
import com.johny.mediaverse.domain.repository.ListenNoteApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ListenNoteApiImp(
    private val httpClient: HttpClient
) : ListenNoteApi {
    override suspend fun getPodcastsPaged(page: Int): Result<PodcastResponseDto, NetworkError> {
        return safeCall<PodcastResponseDto> {
            httpClient.get(
                urlString = Constants.ListenNoteUrls.LISTEN_NOTE_BASE_URL + Constants.ListenNoteUrls.BEST_PODCASTS
            ){
                headers.append("App","MediaVerse")
                headers.append("X-ListenAPI-Key", BuildConfig.LISTEN_NOTE_API_KEY)
                parameter("page",page)
            }
        }
    }
}