package com.johny.mediaverse.domain.repository

import androidx.paging.PagingData
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.domain.model.movie.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<MovieModel>>
    fun getSavedMovieIds(): Flow<Set<Int>>
    suspend fun saveBookmark(movie: MovieEntity)
    suspend fun removeBookmark(movieId: Int)
}