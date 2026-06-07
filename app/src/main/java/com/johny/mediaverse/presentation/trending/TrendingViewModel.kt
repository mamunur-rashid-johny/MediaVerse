package com.johny.mediaverse.presentation.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.domain.model.trending.TrendingModel
import com.johny.mediaverse.domain.repository.TrendingRepository
import com.johny.mediaverse.utils.MediaTypeEnum
import com.johny.mediaverse.utils.toMediaType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */
class TrendingViewModel(
    private val repo: TrendingRepository
) : ViewModel() {

    val savedTvShows = repo.getSavedTvShows().distinctUntilChanged()
    val savedMovies = repo.getSavedMovies().distinctUntilChanged()
    val trending = repo.getTrending().cachedIn(viewModelScope)
    val trends: Flow<PagingData<TrendingUiModel>> =
        combine(trending,savedMovies,savedTvShows){ pagingData,movieId,tvId ->
            pagingData.map { trendingItem ->
                val isBookmarked =
                    when (trendingItem.mediaType.toMediaType()) {
                        MediaTypeEnum.MOVIE -> movieId.any { it == trendingItem.id }
                        MediaTypeEnum.TV -> tvId.any { it == trendingItem.id }
                        else -> false
                    }

                TrendingUiModel(
                    trending = trendingItem,
                    isBookmarked = isBookmarked
                )
            }
        }

    val effect: SharedFlow<TrendingEffect>
        field = MutableSharedFlow<TrendingEffect>()

    fun onIntent(intent: TrendingIntent) = viewModelScope.launch {
        when (intent) {
            is TrendingIntent.OnTrendingItemIntent -> {
                effect.emit(
                    TrendingEffect.NavigateToDetails(
                        intent.trendingModel.id,
                        intent.trendingModel.mediaType.toMediaType()
                    )
                )
            }

            is TrendingIntent.RemoveBookmarkIntent -> {
                removeBookmark(intent.trending)
            }

            TrendingIntent.RetryPaginationIntent -> {
                effect.emit(TrendingEffect.RetryPagination)
            }

            is TrendingIntent.SaveBookmarkIntent -> {
                saveBookmark(intent.trending)
            }
        }
    }

    private fun saveBookmark(trendingModel: TrendingModel) = viewModelScope.launch(Dispatchers.IO) {
        repo.saveBookmark(trendingModel)
    }

    private fun removeBookmark(trendingModel: TrendingModel) = viewModelScope.launch(Dispatchers.IO) {
        repo.removeBookmark(trendingModel)
    }
}