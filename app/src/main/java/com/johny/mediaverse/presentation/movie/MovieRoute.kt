package com.johny.mediaverse.presentation.movie

import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MovieRoute(navController: NavController) {
    val viewModel: MovieViewModel = koinViewModel()
    var showExitDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? android.app.Activity

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            navController.navigate(Destination.OnBoardRoute){
                popUpTo(Destination.MovieRoute) {
                    inclusive = true
                }
            }
        }
    }

    BackHandler {
        showExitDialog = true
    }

    MovieScreen(onIntent = viewModel::onIntent)

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