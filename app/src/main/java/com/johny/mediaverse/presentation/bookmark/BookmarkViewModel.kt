package com.johny.mediaverse.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class BookmarkViewModel : ViewModel() {

    val effect: SharedFlow<BookmarkEffect>
        field = MutableSharedFlow<BookmarkEffect>()

    fun onIntent(intent: BookmarkIntent) = viewModelScope.launch {
        when (intent) {
            is BookmarkIntent.OnNavigateToMovieDetails -> {
                effect.emit(BookmarkEffect.NavigateToMovieDetails(intent.movieId))
            }

            is BookmarkIntent.OnNavigateToPodcastDetails -> {
                effect.emit(BookmarkEffect.NavigateToPodcastDetails(intent.podcast))
            }

            is BookmarkIntent.OnNavigateToTvShowDetails -> {
                effect.emit(BookmarkEffect.NavigateToTvShowDetails(intent.tvShowId))
            }
        }
    }
}