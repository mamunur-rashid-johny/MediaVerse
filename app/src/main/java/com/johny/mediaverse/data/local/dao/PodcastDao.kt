package com.johny.mediaverse.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PodcastDao {

    @Query("SELECT * FROM podcast")
    fun getPagedPodcast(): PagingSource<Int, PodcastEntity>

    @Query("SELECT id FROM podcast")
    fun getPodcast(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePodcast(podcastEntity: PodcastEntity)

    @Query("DELETE FROM podcast WHERE id = :podcastId")
    suspend fun removePodcast(podcastId: String)
}