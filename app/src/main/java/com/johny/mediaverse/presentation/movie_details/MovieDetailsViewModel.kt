package com.johny.mediaverse.presentation.movie_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.johny.mediaverse.core.navigation.Destination

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val route = savedStateHandle.toRoute<Destination.MovieDetailRoute>()
}