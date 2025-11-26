package com.johny.mediaverse.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity

@Database(entities = [PodcastEntity::class], version = 1)
abstract class MediaVerseDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
}