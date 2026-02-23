package com.johny.mediaverse.data.repository

import androidx.compose.ui.unit.Constraints
import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.domain.utils.map
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE_VALUE
import com.johny.mediaverse.data.mapper.toTvShowDetailsModel
import com.johny.mediaverse.data.model.tv_show_details.TvShowDetailsDto
import com.johny.mediaverse.domain.model.tv_show_details.TvShowDetailsModel
import com.johny.mediaverse.domain.repository.TvShowDetailsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

/**
 * Created by Johny on 22/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TvShowDetailsRepositoryImpl(
    private val httpClient: HttpClient
): TvShowDetailsRepository {
    override suspend fun getTvShowDetails(tvShowId: Int): Result<TvShowDetailsModel, NetworkError> {
        return safeCall<TvShowDetailsDto> {
            httpClient.get(
                urlString = BuildConfig.MOVIE_DB_BASE_URL + Constants.MovieDbUrl.TV_DETAILS + "$tvShowId"
            ){
                headers.append(HttpHeaders.Authorization,"Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}")
                headers.append(HttpHeaders.Accept, "application/json")
                parameter(LANGUAGE, LANGUAGE_VALUE)
            }
        }.map {
            it.toTvShowDetailsModel()
        }
    }
}