package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.domain.utils.map
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE_VALUE
import com.johny.mediaverse.data.mapper.toTvShowSeasonModel
import com.johny.mediaverse.data.model.tv_show_season.TvShowSeasonDto
import com.johny.mediaverse.domain.model.tv_show_season.TvShowSeasonModel
import com.johny.mediaverse.domain.repository.SeasonRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class SeasonRepositoryImpl(private val httpClient: HttpClient): SeasonRepository {
    override suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): Result<TvShowSeasonModel, NetworkError> {
        return safeCall<TvShowSeasonDto> {
            httpClient.get(
                urlString = BuildConfig.MOVIE_DB_BASE_URL + Constants.MovieDbUrl.TV_DETAILS + "$seriesId" + Constants.MovieDbUrl.TV_SEASON + "$seasonNumber"
            ){
                headers.append(HttpHeaders.Authorization,"Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}")
                headers.append(HttpHeaders.Accept, "application/json")
                parameter(LANGUAGE, LANGUAGE_VALUE)
            }
        }.map {
            it.toTvShowSeasonModel()
        }
    }
}