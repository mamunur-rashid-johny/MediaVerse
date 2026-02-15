package com.johny.mediaverse

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.ApplicationInfo
import android.os.Build
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
        createNotificationChannel()
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
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelName = "Media Playback"
            val importance = NotificationManager.IMPORTANCE_LOW

            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = "Controls for media playback"
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}