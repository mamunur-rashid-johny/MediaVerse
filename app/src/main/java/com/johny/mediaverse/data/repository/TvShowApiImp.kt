package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.INCLUDE_ADULT
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.INCLUDE_ADULT_VALUE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.INCLUDE_NULL_FIRST_AIR_DATES
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.INCLUDE_NULL_FIRST_AIR_DATES_VALUES
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE_VALUE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.PAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.SORT_BY
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.SORT_BY_VALUE
import com.johny.mediaverse.data.model.tv_show.TvShowResponseDto
import com.johny.mediaverse.domain.repository.TvShowApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

class TvShowApiImp(
    private val httpClient: HttpClient
): TvShowApi {
    override suspend fun getPagedTvShow(page: Int): Result<TvShowResponseDto, NetworkError> {
        return safeCall<TvShowResponseDto> {
            httpClient.get(
                urlString = Constants.MovieDbUrl.MOVIE_DB_BASE_URL+Constants.MovieDbUrl.DISCOVER_TV_SHOW
            ){
                headers.append(HttpHeaders.Authorization,"Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}")
                headers.append(HttpHeaders.Accept, "application/json")
                parameter(INCLUDE_ADULT,"$INCLUDE_ADULT_VALUE")
                parameter(INCLUDE_NULL_FIRST_AIR_DATES,"$INCLUDE_NULL_FIRST_AIR_DATES_VALUES")
                parameter(LANGUAGE, LANGUAGE_VALUE)
                parameter(PAGE,"$page")
                parameter(SORT_BY, SORT_BY_VALUE)
            }
        }
    }
}