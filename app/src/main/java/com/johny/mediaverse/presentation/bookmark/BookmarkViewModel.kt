package com.johny.mediaverse.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val savedPodcast: Flow<PagingData<PodcastEntity>> =
        repository.getPagedPodcast().cachedIn(viewModelScope)

    private val _effect = MutableSharedFlow<BookmarkEffect>()
    val effect: SharedFlow<BookmarkEffect> = _effect

    fun onIntent(intent: BookmarkIntent) = viewModelScope.launch {
        when (intent) {
            is BookmarkIntent.OnNavigateToDetails -> {
                _effect.emit(BookmarkEffect.NavigateToDetails(intent.podcast))
            }

            is BookmarkIntent.OnRemoveBookmark -> {
                removeBookmark(intent.podcastId)
            }
        }
    }

    private fun removeBookmark(podcastId: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeBookmark(podcastId)
    }
}