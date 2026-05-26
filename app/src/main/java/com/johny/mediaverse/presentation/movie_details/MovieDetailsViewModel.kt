package com.johny.mediaverse.presentation.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.johny.mediaverse.core.domain.utils.onError
import com.johny.mediaverse.core.domain.utils.onSuccess
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: MovieDetailsRepository
): ViewModel() {

    val route = savedStateHandle.toRoute<Destination.MovieDetailRoute>()

    var state = MutableStateFlow(MovieDetailsState())
        private set

    private fun getMovieDetails() = viewModelScope.launch(Dispatchers.IO) {
        state.update {
            it.copy(isLoading = true)
        }
        repository.getMovieDetails(route.movieId)
            .onSuccess { movieDetails ->
                state.update {
                    it.copy(
                        isLoading = false,
                        movieDetails = movieDetails
                    )
                }
            }
            .onError { error ->
                state.update {
                    it.copy(
                        isLoading = false,
                        error = error
                    )
                }
            }
    }

    init {
        getMovieDetails()
    }
}