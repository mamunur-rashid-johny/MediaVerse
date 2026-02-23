package com.johny.mediaverse.presentation.tv_show_season_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.johny.mediaverse.core.domain.utils.onError
import com.johny.mediaverse.core.domain.utils.onSuccess
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.domain.repository.SeasonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Created by Johny on 23/2/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TvShowSeasonDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repo: SeasonRepository
) : ViewModel() {
    val route = savedStateHandle.toRoute<Destination.TvShowSeasonDetailRoute>()

    var state = MutableStateFlow(TvShowSeasonState())
        private set

    val effect: SharedFlow<TvShowSeasonSideEffect>
        field = MutableSharedFlow<TvShowSeasonSideEffect>()

    private fun getSeasonDetails() = viewModelScope.launch(Dispatchers.IO) {
        state.update {
            it.copy(isLoading = true)
        }
        repo.getSeasonDetails(route.tvShowId, route.seasonNumber)
            .onSuccess { seasonDetails ->
                state.update {
                    it.copy(
                        isLoading = false,
                        seasonDetails = seasonDetails
                    )
                }
            }
            .onError { it ->
                state.update {
                    it.copy(
                        isLoading = false
                    )
                }
                effect.emit(TvShowSeasonSideEffect.OnShowErrorSideEffect(it))
            }
    }

    init {
        getSeasonDetails()
    }
}