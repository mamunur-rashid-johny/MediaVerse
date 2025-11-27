package com.johny.mediaverse.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.johny.mediaverse.data.local.dao.MovieDao
import com.johny.mediaverse.data.local.model.movie.MovieEntity
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.paging_source.MoviePagingSource
import com.johny.mediaverse.domain.repository.MovieDbApi
import com.johny.mediaverse.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImp(
    private val api: MovieDbApi,
    private val dao: MovieDao,
    private val context: Context
) : MovieRepository {
    override fun getMovies(): Flow<PagingData<MovieModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                MoviePagingSource(api, context)
            }
        ).flow
    }

    override fun getSavedMovieIds(): Flow<Set<Int>> {
        return dao.getMoviesId()
            .map { list -> list.toSet() }
    }

    override suspend fun saveBookmark(movie: MovieEntity) {
        dao.saveMovie(movie)
    }

    override suspend fun removeBookmark(movieId: Int) {
        dao.removeMovie(movieId)
    }
}