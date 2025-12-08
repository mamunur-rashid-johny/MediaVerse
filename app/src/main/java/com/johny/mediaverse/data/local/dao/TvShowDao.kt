package com.johny.mediaverse.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.tv_show.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv_show")
    fun getPagedTvShow(): PagingSource<Int, TvShowEntity>

    @Query("SELECT id FROM tv_show")
    fun getTvShowId(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTvShow(tvShowEntity: TvShowEntity)

    @Query("DELETE FROM tv_show WHERE id = :tvShowId")
    suspend fun removeTvShow(tvShowId: Int)
}