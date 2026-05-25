package com.johny.mediaverse.presentation.tv_show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.core.domain.utils.onError
import com.johny.mediaverse.core.domain.utils.onSuccess
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.repository.TvShowDetailsRepository
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel
import com.johny.mediaverse.presentation.tv_show_details.TvShowDetailsSideEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
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
        when (intent) {
            is TvShowDetailsIntent.OnNavigateToSeriesDetails -> {
                effect.emit(OnNavigateSideEffect(intent.seriesId, intent.seasonNumber))
            }

            is TvShowDetailsIntent.NavigateToDetailsIntent -> {
                effect.emit(NavigateToDetailsEffect(intent.tvShowId))
            }
            is TvShowDetailsIntent.RemoveBookmarkIntent -> {
                removeBookmark(intent.tvShowId)
            }
            is TvShowDetailsIntent.SaveBookmarkIntent -> {
                saveBookmark(intent.tvShowModel)
            }
        }
    }

    init {
        getTvShowDetails()
    }

    val bookmarkFlow = repo.getSavedTvShowIds().distinctUntilChanged()
    val tvShowFlow = repo.getSimilarTvShows(route.tvShowId).cachedIn(viewModelScope)
    val tvShows: Flow<PagingData<TvShowUiModel>> =
        tvShowFlow.combine(bookmarkFlow) { tvShows, bookmarkIds ->
            tvShows.map {
                TvShowUiModel(
                    tvShow = it,
                    isBookmarked = bookmarkIds.contains(it.id)
                )
            }
        }

    private fun saveBookmark(tvShowModel: TvShowModel) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveBookmark(tvShowModel)
    }

    private fun removeBookmark(tvShowId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repo.removeBookmark(tvShowId)
    }
}