package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.johny.mediaverse.domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class TvShowBookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val savedTvShow = repository.getPagedTvShow().cachedIn(viewModelScope)

    val effect: SharedFlow<TvShowBookmarkEffect>
        field = MutableSharedFlow<TvShowBookmarkEffect>()

    fun onIntent(intent: TvShowBookmarkIntent) = viewModelScope.launch {
        when(intent){
            is TvShowBookmarkIntent.OnTvShowBookRemoveIntent -> {
                removeTvShowBookmark(intent.tvShowId)
            }
            is TvShowBookmarkIntent.OnTvShowBookmarkClickIntent -> {
                effect.emit(TvShowBookmarkEffect.OnNavigateToTvShowDetailEffect(intent.tvShowId))
            }
        }
    }

    private fun removeTvShowBookmark(tvShowId: Int) = viewModelScope.launch(Dispatchers.IO){
        repository.removeTvShowBookmark(tvShowId)
    }
}