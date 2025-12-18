package com.johny.mediaverse.presentation.bookmark.tv_show_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.domain.model.tv_show.TvShowModel
import com.johny.mediaverse.domain.repository.BookmarkRepository
import com.johny.mediaverse.presentation.bookmark.tv_show_bookmark.TvShowBookmarkEffect.*
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
        when (intent) {
            is TvShowBookmarkIntent.OnTvShowBookRemoveIntent -> {
                removeTvShowBookmark(intent.tvShowModel)
                effect.emit(ShowMessageEffect(intent.tvShowModel))
            }

            is TvShowBookmarkIntent.OnTvShowBookmarkClickIntent -> {
                effect.emit(OnNavigateToTvShowDetailEffect(intent.tvShowId))
            }

            TvShowBookmarkIntent.OnNavigateToTvShow -> {
                effect.emit(TvShowScreenNavigationEffect)
            }

            is TvShowBookmarkIntent.UndoTvShowIntent -> {
                undoBookmark(intent.tvShowModel)
            }
        }
    }

    private fun removeTvShowBookmark(tvShowModel: TvShowModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeTvShowBookmark(tvShowModel.id)
    }
    private fun undoBookmark(tvShowModel:TvShowModel)=viewModelScope.launch(Dispatchers.IO){
        repository.undoTvShow(tvShowModel.toMovieEntity())
    }
}