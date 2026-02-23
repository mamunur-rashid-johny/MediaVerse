package com.johny.mediaverse.domain.repository

import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.domain.model.tv_show_season.TvShowSeasonModel

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
interface SeasonRepository {
    suspend fun getSeasonDetails(seriesId: Int, seasonNumber: Int): Result<TvShowSeasonModel, NetworkError>
}