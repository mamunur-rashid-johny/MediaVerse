package com.johny.mediaverse.domain.paging_source

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.Constants.Miscellaneous.STARTING_PAGE_INDEX
import com.johny.mediaverse.data.mapper.toMovieModel
import com.johny.mediaverse.domain.model.movie.MovieModel
import com.johny.mediaverse.domain.repository.MovieDbApi

class MoviePagingSource(
    private val api: MovieDbApi,
    private val context: Context
) : PagingSource<Int, MovieModel>() {
    private val seenIds = mutableSetOf<Int>()
    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        seenIds.clear()
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return when (val result = api.getMoviePaged(currentPage)) {
            is Result.Error -> {
                LoadResult.Error(Exception(result.error.toString(context)))
            }

            is Result.Success -> {
                val newUniqueItems = result.data.results.filter {
                    seenIds.add(it.id)
                }
                LoadResult.Page(
                    data = newUniqueItems.map { it.toMovieModel() },
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                    nextKey = if (currentPage < result.data.total_pages) currentPage + 1 else null
                )
            }
        }
    }

}