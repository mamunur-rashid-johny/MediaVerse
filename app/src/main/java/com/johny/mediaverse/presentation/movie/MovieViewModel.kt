package com.johny.mediaverse.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.startup.RemoteConfigDataSource
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.mapper.toMovieEntity
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.repository.MovieRepository
import com.johny.mediaverse.presentation.movie.MovieSideEffect.NavigateToDetails
import com.johny.mediaverse.presentation.movie.MovieSideEffect.NavigateToOnboarding
import com.johny.mediaverse.presentation.movie.model.MovieModelUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MovieViewModel(
    private val preferenceManager: PreferenceManager,
    private val repository: MovieRepository,
    private val remoteConfigDataSource: RemoteConfigDataSource
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

    val effect: SharedFlow<MovieSideEffect>
        field = MutableSharedFlow<MovieSideEffect>()


    fun onIntent(movieIntent: MovieIntent) = viewModelScope.launch {
        when (movieIntent) {
            MovieIntent.LogoutIntent -> {
                preferenceManager.remove(Constants.PreferenceKeys.SHOW_ONBOARDING)
                effect.emit(NavigateToOnboarding)
            }

            is MovieIntent.OnMovieDetailsNavigateIntent -> {
                effect.emit(NavigateToDetails(movieIntent.movieId))
            }

            is MovieIntent.RemoveBookmarkIntent -> {
                removeBookmark(movieIntent.movieId)
            }

            is MovieIntent.SaveBookmarkIntent -> {
                saveBookmark(movieIntent.movie)
            }

            MovieIntent.OnRetryPagination -> {
                effect.emit(MovieSideEffect.RetryPaginationEffect)
            }
        }
    }

    private fun saveBookmark(movieModel: MovieModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveBookmark(movieModel.toMovieEntity())
    }

    private fun removeBookmark(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeBookmark(movieId)
    }
}