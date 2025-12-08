package com.johny.mediaverse.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.johny.mediaverse.data.local.dao.MovieDao
import com.johny.mediaverse.data.local.dao.PodcastDao
import com.johny.mediaverse.data.local.dao.TvShowDao
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity

@Database(
    entities = [PodcastEntity::class, MovieEntity::class, TvShowEntity::class],
    version = 3,
    exportSchema = false
)
abstract class MediaVerseDatabase : RoomDatabase() {
    abstract fun podcastDao(): PodcastDao
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
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
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                         CREATE TABLE IF NOT EXISTS `tv_show` (
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