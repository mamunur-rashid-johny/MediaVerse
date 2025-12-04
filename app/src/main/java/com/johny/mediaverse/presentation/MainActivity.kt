package com.johny.mediaverse.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.johny.mediaverse.core.navigation.BottomBar
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.navigation.MediaVerseApp
import com.johny.mediaverse.core.navigation.NavRail
import com.johny.mediaverse.core.presentation.utils.ObserveAsEvent
import com.johny.mediaverse.core.service.MediaVerseService
import com.johny.mediaverse.core.utils.SnackbarController
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService()
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.isLoading
        }
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MediaVerseTheme {
                App(mainViewModel.currentDestination, windowSizeClass)
            }
        }
    }

    private fun startService() {
        val intent = Intent(this, MediaVerseService::class.java)
        startService(intent)
    }
}

@Composable
fun App(startDestination: Destination, windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val useNavRail = windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact

    //snackbar related
    val scope = rememberCoroutineScope()
    val snackbarState = remember { SnackbarHostState() }
    ObserveAsEvent(events = SnackbarController.event,snackbarState) {event->
        scope.launch {
           snackbarState.currentSnackbarData?.dismiss()
            val result = snackbarState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.ActionPerformed){
                event.action?.action?.invoke()
            }
        }
    }

    val bottomBarRoutes = remember {
        setOf(
            Destination.MovieRoute::class.qualifiedName,
            Destination.TvShowRoute::class.qualifiedName,
            Destination.PodcastRoute::class.qualifiedName,
            Destination.BookmarkRoute::class.qualifiedName
        )
    }
    val bottomBarState = currentRoute in bottomBarRoutes

    if (useNavRail) {
        Row(modifier = Modifier.fillMaxSize()) {
            NavRail(
                navController = navController,
                visibility = bottomBarState
            )

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = snackbarState
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    MediaVerseApp(
                        navController = navController,
                        startDestination = startDestination
                    )
                }
            }
        }
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarState
                )
            },
            bottomBar = {
                BottomBar(
                    visibility = bottomBarState,
                    navController = navController
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                MediaVerseApp(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}



