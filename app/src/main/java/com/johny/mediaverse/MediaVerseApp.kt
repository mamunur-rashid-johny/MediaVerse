package com.johny.mediaverse

import android.app.Application
import com.johny.mediaverse.di.appModule
import com.johny.mediaverse.di.databaseModule
import com.johny.mediaverse.di.repositoryModule
import com.johny.mediaverse.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MediaVerseApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MediaVerseApp)
            modules(appModule, databaseModule,repositoryModule, viewModelModule)
        }
    }
}