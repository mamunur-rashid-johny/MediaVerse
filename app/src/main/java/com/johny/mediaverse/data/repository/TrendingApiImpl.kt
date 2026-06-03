package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE_VALUE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.PAGE
import com.johny.mediaverse.data.model.trending.TrendingResponseDto
import com.johny.mediaverse.domain.repository.TrendingApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TrendingApiImpl(
    private val httpClient: HttpClient
) : TrendingApi {
    override suspend fun getPagedTrending(page: Int): Result<TrendingResponseDto, NetworkError> {
        return safeCall<TrendingResponseDto> {
            httpClient.get(
                urlString = BuildConfig.MOVIE_DB_BASE_URL + Constants.MovieDbUrl.TRENDING_LIST
            ) {
                headers.append(
                    HttpHeaders.Authorization,
                    "Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}"
                )
                headers.append(HttpHeaders.Accept, "application/json")
                parameter(LANGUAGE, LANGUAGE_VALUE)
                parameter(PAGE, "$page")
            }
        }
    }
}