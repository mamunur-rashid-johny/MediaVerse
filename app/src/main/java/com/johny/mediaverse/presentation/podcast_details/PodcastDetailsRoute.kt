package com.johny.mediaverse.presentation.podcast_details

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.navigation.Destination.AudioPlayerRoute
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PodcastDetailsRoute(navController: NavController) {
    val viewModel: PodcastDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showSettingsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as? Activity
    var episode: EpisodeModel? = null

    var hasNotificationPermission by remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            )
        } else {
            mutableStateOf(true)
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasNotificationPermission = isGranted
            if (isGranted){
                episode?.let {
                    viewModel.onIntent(PodcastDetailsIntent.OnAudioPlayIntent(it))
                }
            }
            if (!isGranted && activity != null) {
                val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                )

                if (!shouldShowRationale) {
                    showSettingsDialog = true
                }
            }
        }
    )



    ObserveAsEvent(
        events = viewModel.effect
    ) { effect ->
        when (effect) {
            is PodcastDetailsEffect.NavigateToAudioPlayer -> {
                if (hasNotificationPermission){
                    navController.navigate(
                        AudioPlayerRoute(effect.episodeModel)
                    )
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        episode = effect.episodeModel
                    }
                }
            }

            PodcastDetailsEffect.OnBackPressed -> {
                navController.navigateUp()
            }

            is PodcastDetailsEffect.NavigateToWebviewEffect -> {
                navController.navigate(
                    Destination.WebViewRoute(
                        url = effect.url ?: "",
                        title = effect.title
                    )
                )
            }
        }
    }

    PodcastDetailsScreen(
        state = state,
        onIntent = viewModel::onIntent
    )

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Permission Required") },
            text = { Text("Notification permission was permanently denied. You must enable it in the app settings to receive updates.") },
            confirmButton = {
                TextButton(onClick = {
                    showSettingsDialog = false
                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Go to Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}