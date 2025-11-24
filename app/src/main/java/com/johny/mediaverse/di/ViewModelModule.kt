package com.johny.mediaverse.di

import com.johny.mediaverse.presentation.MainViewModel
import com.johny.mediaverse.presentation.audio_player.AudioPlayerViewModel
import com.johny.mediaverse.presentation.movie.MovieViewModel
import com.johny.mediaverse.presentation.on_board.OnBoardViewModel
import com.johny.mediaverse.presentation.podcast.PodcastViewModel
import com.johny.mediaverse.presentation.podcast_details.PodcastDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::OnBoardViewModel)
    viewModelOf(::MovieViewModel)
    viewModelOf(::PodcastViewModel)
    viewModelOf(::PodcastDetailsViewModel)
    viewModelOf(::AudioPlayerViewModel)
}