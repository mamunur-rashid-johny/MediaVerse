package com.johny.mediaverse.data.repository

import com.johny.mediaverse.BuildConfig
import com.johny.mediaverse.core.data.networking.safeCall
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.domain.utils.map
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE
import com.johny.mediaverse.core.utils.Constants.ApiQueryParam.LANGUAGE_VALUE
import com.johny.mediaverse.data.mapper.toMovieDetails
import com.johny.mediaverse.data.model.movie_details.MovieDetailsDto
import com.johny.mediaverse.domain.model.movie_details.MovieDetailsModel
import com.johny.mediaverse.domain.repository.MovieDetailsRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders

/**
 * Created by Johny on 14/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class MovieDetailsRepositoryImp(
    private val httpClient: HttpClient
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsModel, NetworkError> {
        return safeCall<MovieDetailsDto> {
            httpClient.get(
                urlString = BuildConfig.MOVIE_DB_BASE_URL + Constants.MovieDbUrl.MOVIE_DETAILS +"$movieId"
            ){
                headers.append(HttpHeaders.Authorization,"Bearer ${BuildConfig.MOVIE_DB_ACCESS_TOKEN}")
                headers.append(HttpHeaders.Accept, "application/json")
                parameter(LANGUAGE, LANGUAGE_VALUE)
            }
        }.map {
            it.toMovieDetails()
        }
    }
}