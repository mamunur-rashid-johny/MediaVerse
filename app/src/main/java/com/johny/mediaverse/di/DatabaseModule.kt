package com.johny.mediaverse.di

import androidx.room.Room
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.data.local.database.MediaVerseDatabase
import com.johny.mediaverse.data.local.database.MediaVerseDatabase.Companion.MIGRATION_1_2
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MediaVerseDatabase::class.java,
            Constants.Miscellaneous.DATABASE_NAME
        ).addMigrations(MIGRATION_1_2)
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single {
        get<MediaVerseDatabase>().podcastDao()
    }

    single {
        get<MediaVerseDatabase>().movieDao()
    }
}