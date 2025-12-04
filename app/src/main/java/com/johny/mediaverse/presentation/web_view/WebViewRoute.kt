package com.johny.mediaverse.presentation.web_view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.presentation.tv_show_details.TvDetailsScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun WebViewRoute(navController: NavController) {
    val viewModel: WebViewViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(events = viewModel.effect) { effect ->
        when(effect){
            WebviewEffect.OnBackPressedEffect ->{
                navController.navigateUp()
            }
        }
    }


    WebViewScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}