package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.domain.repository.BookmarkRepository
import com.johny.mediaverse.presentation.bookmark.movie_bookmark.MovieBookmarkEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MovieBookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val savedMovie: Flow<PagingData<MovieEntity>> =
        repository.getPagedMovie().cachedIn(viewModelScope)

    val effect:SharedFlow<MovieBookmarkEffect>
        field = MutableSharedFlow<MovieBookmarkEffect>()

    fun onIntent(intent: MovieBookmarkIntent)=viewModelScope.launch{
        when(intent){
            is MovieBookmarkIntent.OnMovieBookRemoveIntent ->{
                removeMovieBookmark(intent.movieId)
            }
            is MovieBookmarkIntent.OnMovieBookmarkClickIntent ->{
                effect.emit(OnNavigateToMovieDetailEffect(intent.movieId))
            }

            MovieBookmarkIntent.OnNavigateToMovie -> {
                effect.emit(MovieScreenNavigationEffect)
            }
        }
    }

    private fun removeMovieBookmark(movieId: Int)=viewModelScope.launch(Dispatchers.IO){
        repository.removeMovieBookmark(movieId)
    }
}