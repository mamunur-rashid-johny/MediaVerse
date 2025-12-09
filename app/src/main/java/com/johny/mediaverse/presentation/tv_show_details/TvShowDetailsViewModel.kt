package com.johny.mediaverse.presentation.tv_show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.johny.mediaverse.core.navigation.Destination

class TvShowDetailsViewModel(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    val route = savedStateHandle.toRoute<Destination.TvShowDetailRoute>()
}