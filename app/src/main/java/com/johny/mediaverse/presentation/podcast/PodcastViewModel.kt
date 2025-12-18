package com.johny.mediaverse.presentation.podcast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.PodcastRepository
import com.johny.mediaverse.presentation.podcast.PodcastEffect.NavigateToDetail
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class PodcastViewModel(
    private val repository: PodcastRepository
) : ViewModel() {

    val pagingFlow: Flow<PagingData<Podcast>> = repository.getBestPodcasts()
        .cachedIn(viewModelScope)

    private val bookmarksFlow = repository.getSavedPodcastIds()

    val podcast: Flow<PagingData<PodcastUIModel>> = pagingFlow
        .combine(bookmarksFlow) { pagingData, bookmarkIds ->
            pagingData.map { podcast ->
                PodcastUIModel(
                    podcast = podcast,
                    isBookmark = bookmarkIds.contains(podcast.id)
                )
            }
        }

    val effect: SharedFlow<PodcastEffect>
        field = MutableSharedFlow<PodcastEffect>()

    fun onIntent(intent: PodcastIntent) = viewModelScope.launch {
        when (intent) {
            is PodcastIntent.OnItemClick -> {
                effect.emit(NavigateToDetail(intent.podcast))
            }

            is PodcastIntent.OnAddBookMark -> {
                addPodcastBookmark(intent.podcast)
            }

            is PodcastIntent.OnRemoveBookmark -> {
                removeBookmark(intent.podcastId)
            }

            PodcastIntent.RetryPagination -> {
                effect.emit(PodcastEffect.RetryPaginationEffect)
            }
        }
    }

    private fun addPodcastBookmark(podcast: Podcast) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveBookmark(podcast)
    }

    private fun removeBookmark(podcastId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeBookmark(podcastId)
    }
}