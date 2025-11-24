package com.johny.mediaverse.di

import androidx.media3.exoplayer.ExoPlayer
import com.johny.mediaverse.core.data.networking.HttpClientFactory
import com.johny.mediaverse.core.data.pref.PreferenceManager
import com.johny.mediaverse.presentation.MainViewModel
import com.johny.mediaverse.presentation.movie.MovieViewModel
import com.johny.mediaverse.presentation.on_board.OnBoardViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val  appModule = module {
    //ktor-client
    single{ HttpClientFactory.create(CIO.create()) }
    //shared preference
    single { PreferenceManager(androidContext()) }

    //exo-player
    single{ ExoPlayer.Builder(androidContext()).build() }
}