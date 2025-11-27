package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.model.movie.MovieResponseDto
import com.johny.mediaverse.domain.repository.MovieDbApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

class MovieDbApiImp(
    private val httpClient: HttpClient
): MovieDbApi {
    override suspend fun getMoviePaged(page: Int): Result<MovieResponseDto, NetworkError> {
        return safeCall<MovieResponseDto> {
            httpClient.get(
                urlString = Constants.MovieDbUrl.MOVIE_DB_BASE_URL+Constants.MovieDbUrl.DISCOVER_MOVIE
            ){
                headers.append(HttpHeaders.Authorization,"Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}")
                headers.append(HttpHeaders.Accept, "application/json")

                //parameter
                parameter(INCLUDE_ADULT,"$INCLUDE_ADULT_VALUE")
                parameter(INCLUDE_VIDEO,"$INCLUDE_VIDEO_VALUE")
                parameter(LANGUAGE, LANGUAGE_VALUE)
                parameter("page","$page")
                parameter(SORT_BY, SORT_BY_VALUE)
            }
        }
    }
}

const val INCLUDE_ADULT = "include_adult"
const val INCLUDE_ADULT_VALUE = true
const val INCLUDE_VIDEO = "include_video"
const val INCLUDE_VIDEO_VALUE = true
const val LANGUAGE = "language"
const val LANGUAGE_VALUE = "en-US"
const val SORT_BY = "sort_by"
const val SORT_BY_VALUE = "popularity.desc"
