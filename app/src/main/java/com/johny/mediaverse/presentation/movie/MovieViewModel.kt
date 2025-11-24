package com.johny.mediaverse.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    private val _effect = MutableSharedFlow<MovieSideEffect>()
    val effect = _effect.asSharedFlow()

    fun onIntent(movieIntent: MovieIntent) = viewModelScope.launch {
        when (movieIntent) {
            MovieIntent.LogoutIntent -> {
                preferenceManager.remove(Constants.PreferenceKeys.SHOW_ONBOARDING)
                _effect.emit(MovieSideEffect.NavigateToOnboarding)
            }
        }
    }
}