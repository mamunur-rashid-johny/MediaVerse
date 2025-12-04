package com.johny.mediaverse.presentation.movie

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.johny.mediaverse.core.navigation.Destination.MovieDetailRoute
import com.johny.mediaverse.core.navigation.Destination.MovieRoute
import com.johny.mediaverse.core.navigation.Destination.OnBoardRoute
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.utils.SnackbarAction
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.core.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MovieRoute(navController: NavController) {
    val viewModel: MovieViewModel = koinViewModel()
    val moviesPager = viewModel.movies.collectAsLazyPagingItems()
    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? android.app.Activity
    val scope = rememberCoroutineScope()

    ObserveAsEvent(events = viewModel.effect) { effect ->
        when (effect) {
            is MovieSideEffect.NavigateToDetails -> {
                navController.navigate(MovieDetailRoute(effect.movieId))
            }

            MovieSideEffect.NavigateToOnboarding -> {
                navController.navigate(OnBoardRoute) {
                    popUpTo(MovieRoute) {
                        inclusive = true
                    }
                }
            }

            is MovieSideEffect.ShowError -> {
                scope.launch {
                    SnackbarController.sendEvent(
                        SnackbarEvent(
                            message = effect.message,
                            action = SnackbarAction(
                                name = effect.actionLabel,
                                action = {
                                    moviesPager.retry()
                                }
                            )
                        )
                    )
                }
            }
        }
    }

    BackHandler {
        showExitDialog = true
    }

    MovieScreen(
        movies = moviesPager,
        onIntent = viewModel::onIntent
    )

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = {
                showExitDialog = false
            },
            title = {
                Text(text = "Confirm Exit")
            },
            text = {
                Text(text = "Are you sure you want to exit the app?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        activity?.finish()
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}