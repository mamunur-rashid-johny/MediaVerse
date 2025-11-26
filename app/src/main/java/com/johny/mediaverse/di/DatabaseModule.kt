package com.johny.mediaverse.di

import androidx.room.Room
import com.johny.mediaverse.data.local.database.MediaVerseDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MediaVerseDatabase::class.java,
            "media_verse_db"
        ).fallbackToDestructiveMigration(true)
            .build()
    }

    single {
        get<MediaVerseDatabase>().podcastDao()
    }
}