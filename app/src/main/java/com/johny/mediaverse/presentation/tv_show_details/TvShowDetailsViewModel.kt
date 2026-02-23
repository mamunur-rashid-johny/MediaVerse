package com.johny.mediaverse.presentation.tv_show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.johny.mediaverse.core.domain.utils.onError
import com.johny.mediaverse.core.domain.utils.onSuccess
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.domain.repository.TvShowDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvShowDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repo: TvShowDetailsRepository
) : ViewModel() {

    val route = savedStateHandle.toRoute<Destination.TvShowDetailRoute>()

    var state = MutableStateFlow(TvShowDetailsState())
        private set

    val effect: SharedFlow<TvShowDetailsSideEffect>
        field = MutableSharedFlow<TvShowDetailsSideEffect>()

    private fun getTvShowDetails() = viewModelScope.launch(Dispatchers.IO) {
        state.update {
            it.copy(isLoading = true)
        }
        repo.getTvShowDetails(route.tvShowId)
            .onSuccess { tvShowDetailsModel ->
                state.update {
                    it.copy(
                        isLoading = false,
                        tvShowDetails = tvShowDetailsModel
                    )
                }
            }
            .onError {
                state.update {
                    it.copy(isLoading = false)
                }
                effect.emit(TvShowDetailsSideEffect.ShowErrorMessage(it))
            }
    }

    fun onIntent(intent: TvShowDetailsIntent) = viewModelScope.launch {
        when(intent){
            is TvShowDetailsIntent.OnNavigateToSeriesDetails -> {
                effect.emit(TvShowDetailsSideEffect.OnNavigateSideEffect(intent.seriesId,intent.seasonNumber))
            }
        }
    }

    init {
        getTvShowDetails()
    }
}