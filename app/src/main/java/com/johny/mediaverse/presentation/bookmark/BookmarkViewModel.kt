package com.johny.mediaverse.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johny.mediaverse.presentation.bookmark.BookmarkEffect.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {

    val state: StateFlow<BookmarkState>
        field = MutableStateFlow(BookmarkState())


    val effect: SharedFlow<BookmarkEffect>
        field = MutableSharedFlow<BookmarkEffect>()

    fun onIntent(intent: BookmarkIntent) = viewModelScope.launch {
        when (intent) {
            is BookmarkIntent.OnNavigateToMovieDetails -> {
                effect.emit(NavigateToMovieDetails(intent.movieId))
            }

            is BookmarkIntent.OnNavigateToPodcastDetails -> {
                effect.emit(NavigateToPodcastDetails(intent.podcast))
            }

            is BookmarkIntent.OnNavigateToTvShowDetails -> {
                effect.emit(NavigateToTvShowDetails(intent.tvShowId))
            }

            is BookmarkIntent.OnUpdateTabIndex -> {
                updateIndex(intent.index)
            }
        }
    }

    private fun updateIndex(index: Int) {
        state.update {
            it.copy(selectedTabIndex = index)
        }
    }
}