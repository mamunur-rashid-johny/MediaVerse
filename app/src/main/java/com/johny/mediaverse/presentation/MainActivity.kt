package com.johny.mediaverse.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.johny.mediaverse.core.navigation.BottomBar
import com.johny.mediaverse.core.navigation.Destination
import com.johny.mediaverse.core.navigation.MediaVerseApp
import com.johny.mediaverse.core.service.MediaVerseService
import com.johny.mediaverse.presentation.ui.theme.MediaVerseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

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
            MediaVerseTheme {
                App(mainViewModel.currentDestination)
            }
        }
    }

    private fun startService() {
        val intent = Intent(this, MediaVerseService::class.java)
        startService(intent)
    }
}

@Composable
fun App(startDestination: Destination) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = remember {
        setOf(
            Destination.MovieRoute::class.qualifiedName,
            Destination.TvShowRoute::class.qualifiedName,
            Destination.PodcastRoute::class.qualifiedName,
            Destination.BookmarkRoute::class.qualifiedName
        )
    }
    val bottomBarState = currentRoute in bottomBarRoutes

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                visibility = bottomBarState,
                navController = navController
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MediaVerseApp(
                navController = navController,
                startDestination = startDestination
            )
        }
    }
}



