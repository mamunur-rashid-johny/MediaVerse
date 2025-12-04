package com.johny.mediaverse.core.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.johny.mediaverse.core.utils.serializableType
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import com.johny.mediaverse.presentation.audio_player.AudioPlayerRoute
import com.johny.mediaverse.presentation.bookmark.BookmarkRoute
import com.johny.mediaverse.presentation.movie.MovieRoute
import com.johny.mediaverse.presentation.movie_details.MovieDetailsRoute
import com.johny.mediaverse.presentation.on_board.OnBoardRoute
import com.johny.mediaverse.presentation.podcast.PodcastRoute
import com.johny.mediaverse.presentation.podcast_details.PodcastDetailsRoute
import com.johny.mediaverse.presentation.settings.SettingsRoute
import com.johny.mediaverse.presentation.tv_show.TvShowRoute
import com.johny.mediaverse.presentation.tv_show_details.TvShowDetailsRoute
import com.johny.mediaverse.presentation.tv_show_season_details.TvShowSeasonDetailsRoute
import com.johny.mediaverse.presentation.web_view.WebViewRoute
import kotlin.reflect.typeOf


@Composable
fun MediaVerseApp(
    navController: NavHostController,
    startDestination: Destination
) {
    val animationSpec = tween<Float>(durationMillis = 1000)
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(animationSpec = animationSpec)
        },
        exitTransition = {
            fadeOut(animationSpec = animationSpec)
        },
        popEnterTransition = {
            fadeIn(animationSpec = animationSpec)
        },
        popExitTransition = {
            fadeOut(animationSpec = animationSpec)
        }
    ) {
        composable<Destination.OnBoardRoute> {
            OnBoardRoute(navController = navController)
        }
        composable<Destination.MovieRoute> {
            MovieRoute(navController = navController)
        }
        composable<Destination.TvShowRoute> {
            TvShowRoute(navController = navController)
        }
        composable<Destination.PodcastRoute> {
            PodcastRoute(navController = navController)
        }
        composable<Destination.BookmarkRoute> {
            BookmarkRoute(navController = navController)
        }
        composable<Destination.MovieDetailRoute> {
            MovieDetailsRoute(navController = navController)
        }
        composable<Destination.TvShowDetailRoute> {
            TvShowDetailsRoute(navController = navController)
        }
        composable<Destination.PodcastDetailRoute>(
            typeMap = mapOf(
                typeOf<Podcast>() to serializableType<Podcast>()
            )
        ) {
            PodcastDetailsRoute(navController = navController)
        }
        composable<Destination.SettingsRoute> {
            SettingsRoute(navController = navController)
        }
        composable<Destination.TvShowSeasonDetailRoute> {
            TvShowSeasonDetailsRoute(navController = navController)
        }
        composable<Destination.AudioPlayerRoute>(
            typeMap = mapOf(
                typeOf<EpisodeModel>() to serializableType<EpisodeModel>()
            )
        ) {
            AudioPlayerRoute(navController = navController)
        }
        composable<Destination.WebViewRoute> {
            WebViewRoute(navController = navController)
        }
    }
}