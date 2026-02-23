package com.johny.mediaverse.core.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.johny.mediaverse.R
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import kotlinx.serialization.Serializable

//All destination of the app
@Serializable
sealed interface Destination {
    @Serializable
    data object OnBoardRoute : Destination

    @Serializable
    data object MovieRoute : Destination

    @Serializable
    data class MovieDetailRoute(val movieId: Int) : Destination

    @Serializable
    data object TvShowRoute : Destination

    @Serializable
    data class TvShowDetailRoute(val tvShowId: Int) : Destination

    @Serializable
    data class TvShowSeasonDetailRoute(val tvShowId: Int, val seasonNumber: Int) : Destination

    @Serializable
    data object PodcastRoute : Destination

    @Serializable
    data class PodcastDetailRoute(val podcast: Podcast) : Destination

    @Serializable
    data class AudioPlayerRoute(val episodeModel: EpisodeModel) : Destination

    @Serializable
    data object BookmarkRoute : Destination

    @Serializable
    data object SettingsRoute : Destination

    @Serializable
    data class WebViewRoute(val url: String, val title: String?) : Destination

}

//These Item Used to Create Bottom Navigation Bar
sealed class BottomNavItem(
    @param:StringRes val title: Int,
    @param:DrawableRes val iconSelected: Int,
    @param:DrawableRes val iconUnselected: Int,
    val color: Color,
    val route: Destination
) {
    data object Movie : BottomNavItem(
        R.string.movie,
        R.drawable.movie_selected,
        R.drawable.movie,
        color = Color(0xFFFFA574),
        Destination.MovieRoute
    )

    data object TvShow : BottomNavItem(
        R.string.tv_show,
        R.drawable.tv_show_selected,
        R.drawable.tv_show,
        color = Color(0xFFFA6FFF) ,
        Destination.TvShowRoute
    )

    data object Podcast : BottomNavItem(
        R.string.podcast,
        R.drawable.podcast_selected,
        R.drawable.podcast,
        color = Color(0xFFADFF64),
        Destination.PodcastRoute
    )

    data object Bookmark : BottomNavItem(
        R.string.bookmark,
        R.drawable.bookmark_selected,
        R.drawable.bookmark,
        color = Color(0xFF009688),
        Destination.BookmarkRoute
    )
}
