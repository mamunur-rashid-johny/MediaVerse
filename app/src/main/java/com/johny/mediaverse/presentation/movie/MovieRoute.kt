package com.johny.mediaverse.presentation.movie

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.johny.mediaverse.utils.checkInternet
import com.johny.mediaverse.utils.openConnectivitySettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MovieRoute(navController: NavController) {
    val viewModel: MovieViewModel = koinViewModel()
    val moviesPager = viewModel.movies.collectAsLazyPagingItems()
    val showExitDialog = remember { mutableStateOf(false) }
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

            MovieSideEffect.RetryPaginationEffect -> {
                if (context.checkInternet()) {
                    moviesPager.retry()
                } else {
                    scope.launch {
                        SnackbarController.sendEvent(
                            SnackbarEvent(
                                message = "No Internet Connection is Available",
                                action = SnackbarAction(
                                    name = "Settings",
                                    action = {
                                        context.openConnectivitySettings()
                                    }
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    BackHandler {
        showExitDialog.value = true
    }

    MovieScreen(
        movies = moviesPager,
        onIntent = viewModel::onIntent
    )

    if (showExitDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showExitDialog.value = false
            },
            title = {
                Text(
                    text = "Confirm Exit",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to exit the app?",
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog.value = false
                        activity?.finish()
                    }
                ) {
                    Text("Yes", style = MaterialTheme.typography.labelLarge)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog.value = false
                    }
                ) {
                    Text("No", style = MaterialTheme.typography.labelLarge)
                }
            }
        )
    }
}