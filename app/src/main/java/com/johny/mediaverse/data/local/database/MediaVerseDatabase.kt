package com.johny.mediaverse.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.johny.mediaverse.data.local.dao.MovieDao
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity

@Database(entities = [PodcastEntity::class, MovieEntity::class], version = 2, exportSchema = false)
abstract class MediaVerseDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
    abstract fun movieDao(): MovieDao

    companion object{
        val MIGRATION_1_2 = object: Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS `movie` (
                        `id` INTEGER NOT NULL, 
                        `title` TEXT NOT NULL, 
                        `rating` REAL NOT NULL,
                        `releaseDate` TEXT NOT NULL,
                        `posterPath` TEXT NOT NULL, 
                        `savedAt` INTEGER NOT NULL, 
                        PRIMARY KEY(`id`)
                    )
                    """
                )
            }
        }
    }
}