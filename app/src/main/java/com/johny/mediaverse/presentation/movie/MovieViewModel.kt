package com.johny.mediaverse.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.repository.MovieRepository
import com.johny.mediaverse.presentation.movie.MovieSideEffect.*
import com.johny.mediaverse.presentation.movie.model.MovieModelUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MovieViewModel(
    private val preferenceManager: PreferenceManager,
    private val repository: MovieRepository
) : ViewModel() {

    val moviesFlow: Flow<PagingData<MovieModel>> = repository.getMovies().cachedIn(viewModelScope)

    val bookmarkFlow = repository.getSavedMovieIds().distinctUntilChanged()

    val movies: Flow<PagingData<MovieModelUi>> =
        moviesFlow.combine(bookmarkFlow) { movies, bookmarkIds ->
            movies.map { movie ->
                MovieModelUi(
                    movie = movie,
                    isBookmark = bookmarkIds.contains(movie.id)
                )
            }
        }.cachedIn(viewModelScope)

    private val _effect = MutableSharedFlow<MovieSideEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(movieIntent: MovieIntent) = viewModelScope.launch {
        when (movieIntent) {
            MovieIntent.LogoutIntent -> {
                preferenceManager.remove(Constants.PreferenceKeys.SHOW_ONBOARDING)
                _effect.emit(NavigateToOnboarding)
            }

            is MovieIntent.OnMovieDetailsNavigateIntent -> {
                _effect.emit(NavigateToDetails(movieIntent.movieId))
            }
            is MovieIntent.RemoveBookmarkIntent -> {
                removeBookmark(movieIntent.movieId)
            }
            is MovieIntent.SaveBookmarkIntent -> {
                saveBookmark(movieIntent.movie)
            }

            is MovieIntent.ShowErrorIntent -> {
                _effect.emit(ShowError(movieIntent.message,movieIntent.actionLabel))
            }
        }
    }

    private fun saveBookmark(movieModel: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveBookmark(movieModel.toMovieEntity())
    }

    private fun removeBookmark(movieId: Int)=viewModelScope.launch(Dispatchers.IO){
        repository.removeBookmark(movieId)
    }
}