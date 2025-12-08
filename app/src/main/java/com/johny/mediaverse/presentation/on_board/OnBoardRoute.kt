package com.johny.mediaverse.presentation.on_board

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnBoardRoute(navController: NavController) {

    val viewModel: OnBoardViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.effect) { effect->
        when(effect){
            OnBoardEffect.NavigateToHome -> {
                navController.navigate(Destination.MovieRoute){
                    popUpTo(Destination.OnBoardRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }

    OnBoardScreen(
        modifier = Modifier.fillMaxSize(),
        onBoardState = state,
        onBoardEvent = viewModel::onIntent
    )

}