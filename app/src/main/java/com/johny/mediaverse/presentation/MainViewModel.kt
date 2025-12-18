package com.johny.mediaverse.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.domain.utils.ConnectivityObserver
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.utils.Constants
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    preferenceManager: PreferenceManager,
    conn: ConnectivityObserver
) : ViewModel() {

    val isConnected = conn
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    val onBoardShown = preferenceManager.get(Constants.PreferenceKeys.SHOW_ONBOARDING, false)

    val currentDestination = if (onBoardShown) Destination.MovieRoute else Destination.OnBoardRoute

    var isLoading by mutableStateOf(true)
        private set

    init {
        appLoading()
    }


    fun appLoading() = viewModelScope.launch {
        val minDelay = async { delay(5000) }
        minDelay.await()
        isLoading = false
    }
}