package com.johny.mediaverse.presentation.web_view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.johny.mediaverse.core.navigation.Destination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WebViewViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val route = savedStateHandle.toRoute<Destination.WebViewRoute>()

    var state = MutableStateFlow(WebviewState())
        private set

    private val _effect = MutableSharedFlow<WebviewEffect>()
    val effect: SharedFlow<WebviewEffect> = _effect

    init {
        state.update {
            it.copy(url = route.url, title = route.title)
        }
    }

    fun onIntent(intent: WebViewIntent) = viewModelScope.launch {
        when (intent) {
            WebViewIntent.OnBackPressedIntent -> {
                _effect.emit(WebviewEffect.OnBackPressedEffect)
            }

            is WebViewIntent.OnLoadingIntent -> {
                state.update {
                    it.copy(isLoading = intent.isLoading)
                }
            }

            is WebViewIntent.OnFirstPageLoadIntent -> {
                state.update {
                    it.copy(pageFirstLoaded = intent.value)
                }
            }
        }
    }
}