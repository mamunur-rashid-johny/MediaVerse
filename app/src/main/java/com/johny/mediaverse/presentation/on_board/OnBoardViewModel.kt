package com.johny.mediaverse.presentation.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnBoardViewModel(
    private val preferenceManager: PreferenceManager
) : ViewModel() {

    val state: StateFlow<OnBoardState>
        field = MutableStateFlow(OnBoardState())
    val effect: SharedFlow<OnBoardEffect>
        field = MutableSharedFlow<OnBoardEffect>()


    fun onIntent(onBoardIntent: OnBoardIntent) = viewModelScope.launch {
        when (onBoardIntent) {
            OnBoardIntent.SaveOnBoardIntent -> {
                preferenceManager.put(Constants.PreferenceKeys.SHOW_ONBOARDING, true)
                effect.emit(OnBoardEffect.NavigateToHome)
            }
        }
    }

    init {
        initOnBoardScreen()
    }

    fun initOnBoardScreen() {
        (state as MutableStateFlow).update {
            it.copy(
                onBoardInfo = dataSets
            )
        }
    }
}