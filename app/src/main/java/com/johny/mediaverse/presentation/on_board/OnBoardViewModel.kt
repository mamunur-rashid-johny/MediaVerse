package com.johny.mediaverse.presentation.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.core.utils.Constants
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnBoardViewModel(
    private val preferenceManager: PreferenceManager
): ViewModel() {

    private val _state = MutableStateFlow(OnBoardState())
    val state: StateFlow<OnBoardState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<OnBoardEffect>()
    val effect: SharedFlow<OnBoardEffect> = _effect.asSharedFlow()




    fun onIntent(onBoardIntent: OnBoardIntent)=viewModelScope.launch{
        when(onBoardIntent){
            OnBoardIntent.SaveOnBoardIntent -> {
                preferenceManager.put(Constants.PreferenceKeys.SHOW_ONBOARDING,true)
                _effect.emit(OnBoardEffect.NavigateToHome)
            }
        }
    }
}