package com.johny.mediaverse

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import com.johny.mediaverse.di.appModule
import com.johny.mediaverse.di.databaseModule
import com.johny.mediaverse.di.repositoryModule
import com.johny.mediaverse.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MediaVerseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setStrictModePolicy()
        startKoin {
            androidLogger()
            androidContext(this@MediaVerseApp)
            modules(appModule, databaseModule, repositoryModule, viewModelModule)
        }
    }

    private fun isDebuggable(): Boolean {
        return 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
    }

    private fun setStrictModePolicy() {
        if (isDebuggable()) {
            StrictMode.setThreadPolicy(
                Builder().detectAll().penaltyLog().build(),
            )
        }
    }
}