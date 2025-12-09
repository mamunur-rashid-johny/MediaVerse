package com.johny.mediaverse.di

import com.johny.mediaverse.presentation.MainViewModel
import com.johny.mediaverse.presentation.audio_player.AudioPlayerViewModel
import com.johny.mediaverse.presentation.bookmark.movie_bookmark.MovieBookmarkViewModel
import com.johny.mediaverse.presentation.bookmark.podcast_bookmark.PodcastBookmarkViewModel
import com.johny.mediaverse.presentation.bookmark.tv_show_bookmark.TvShowBookmarkViewModel
import com.johny.mediaverse.presentation.movie.MovieViewModel
import com.johny.mediaverse.presentation.movie_details.MovieDetailsViewModel
import com.johny.mediaverse.presentation.on_board.OnBoardViewModel
import com.johny.mediaverse.presentation.podcast.PodcastViewModel
import com.johny.mediaverse.presentation.podcast_details.PodcastDetailsViewModel
import com.johny.mediaverse.presentation.tv_show.TvShowViewModel
import com.johny.mediaverse.presentation.tv_show_details.TvShowDetailsViewModel
import com.johny.mediaverse.presentation.web_view.WebViewViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::OnBoardViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::PodcastViewModel)
    viewModelOf(::PodcastDetailsViewModel)
    viewModelOf(::AudioPlayerViewModel)
    viewModelOf(::PodcastBookmarkViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::MovieDetailsViewModel)
    viewModelOf(::WebViewViewModel)
    viewModelOf(::MovieBookmarkViewModel)
    viewModelOf(::TvShowViewModel)
    viewModelOf(::TvShowBookmarkViewModel)
    viewModelOf(::TvShowDetailsViewModel)
}