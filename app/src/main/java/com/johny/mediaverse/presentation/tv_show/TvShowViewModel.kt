package com.johny.mediaverse.presentation.tv_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.repository.TvShowRepository
import com.johny.mediaverse.presentation.tv_show.TvShowSideEffect.*
import com.johny.mediaverse.presentation.tv_show.model.TvShowUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class TvShowViewModel(
    private val repository: TvShowRepository
) : ViewModel() {

    val bookmarkFlow = repository.getSavedTvShowIds().distinctUntilChanged()
    val tvShowFlow = repository.getMovies().cachedIn(viewModelScope)
    val tvShows: Flow<PagingData<TvShowUiModel>> =
        tvShowFlow.combine(bookmarkFlow) { tvShows, bookmarkIds ->
            tvShows.map {
                TvShowUiModel(
                    tvShow = it,
                    isBookmarked = bookmarkIds.contains(it.id)
                )
            }
        }


    val effect: SharedFlow<TvShowSideEffect>
        field = MutableSharedFlow<TvShowSideEffect>()


    fun onIntent(intent: TvShowIntent) = viewModelScope.launch {
        when (intent) {
            is TvShowIntent.NavigateToDetailsIntent -> {
                effect.emit(NavigateToDetailsEffect(intent.tvShowId))
            }

            is TvShowIntent.RemoveBookmarkIntent -> {
                removeBookmark(intent.tvShowId)
            }

            is TvShowIntent.SaveBookmarkIntent -> {
                saveBookmark(intent.tvShowModel)
            }

            TvShowIntent.OnRetryPagination -> {
                effect.emit(RetryPaginationEffect)
            }
        }
    }


    private fun saveBookmark(tvShowModel: TvShowModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveBookmark(tvShowModel)
    }

    private fun removeBookmark(tvShowId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeBookmark(tvShowId)
    }


}