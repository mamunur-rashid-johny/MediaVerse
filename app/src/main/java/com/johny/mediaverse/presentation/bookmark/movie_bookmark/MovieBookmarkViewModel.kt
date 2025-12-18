package com.johny.mediaverse.presentation.bookmark.movie_bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.domain.model.movie.MovieModel
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
                removeMovieBookmark(intent.movieModel.id)
                effect.emit(ShowMessageEffect(intent.movieModel))
            }
            is MovieBookmarkIntent.OnMovieBookmarkClickIntent ->{
                effect.emit(OnNavigateToMovieDetailEffect(intent.movieId))
            }

            MovieBookmarkIntent.OnNavigateToMovie -> {
                effect.emit(MovieScreenNavigationEffect)
            }

            is MovieBookmarkIntent.ONMovieBookmarkUndoIntent -> {
                undoBookmark(intent.movieModel)
            }
        }
    }

    private fun removeMovieBookmark(movieId: Int)=viewModelScope.launch(Dispatchers.IO){
        repository.removeMovieBookmark(movieId)
    }

    private fun undoBookmark(movieModel: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.undoMovie(movieModel.toMovieEntity())
    }
}