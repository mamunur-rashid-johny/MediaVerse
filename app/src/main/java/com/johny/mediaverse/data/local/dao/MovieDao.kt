package com.johny.mediaverse.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getPagedMovie(): PagingSource<Int, MovieEntity>

    @Query("SELECT id FROM movie")
    fun getMoviesId(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movieEntity: MovieEntity)

    @Query("DELETE FROM podcast WHERE id = :movieId")
    suspend fun removeMovie(movieId: Int)
}