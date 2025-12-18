package com.johny.mediaverse.presentation.bookmark.podcast_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.mapper.toPodcastEntity
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.BookmarkRepository
import com.johny.mediaverse.presentation.bookmark.podcast_bookmark.PodcastBookmarkEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class PodcastBookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val savedPodcast: Flow<PagingData<PodcastEntity>> =
        repository.getPagedPodcast().cachedIn(viewModelScope)

    val effect: SharedFlow<PodcastBookmarkEffect>
        field = MutableSharedFlow<PodcastBookmarkEffect>()

    fun onIntent(intent: PodcastBookmarkIntent) = viewModelScope.launch {
        when (intent) {
            is PodcastBookmarkIntent.OnPodcastBookRemoveIntent ->{
                removeBookmark(intent.podcast)
                effect.emit(ShowMessageEffect(intent.podcast))
            }
            is PodcastBookmarkIntent.OnPodcastBookmarkClickIntent ->{
                effect.emit(OnNavigateToPodcastDetailEffect(intent.podcast))
            }

            PodcastBookmarkIntent.OnNavigateToPodcast -> {
                effect.emit(PodcastScreenNavigationEffect)
            }

            is PodcastBookmarkIntent.UndoPodcastIntent -> {
                undoBookmark(intent.podcast)
            }
        }
    }

    private fun removeBookmark(podcast: Podcast) = viewModelScope.launch(Dispatchers.IO) {
        repository.removePodcastBookmark(podcast.id)
    }

    private fun undoBookmark(podcast: Podcast) = viewModelScope.launch(Dispatchers.IO) {
        repository.undoPodcast(podcast.toPodcastEntity())
    }
}