package com.johny.mediaverse.domain.paging_source

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.Constants.Miscellaneous.STARTING_PAGE_INDEX
import com.johny.mediaverse.data.mapper.toPodcasts
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.repository.ListenNoteApi

class PodcastPagingSource(
    private val api: ListenNoteApi,
    private val context: Context
) : PagingSource<Int, Podcast>() {
    override fun getRefreshKey(state: PagingState<Int, Podcast>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Podcast> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return when (val result = api.getPodcastsPaged(currentPage)) {
            is Result.Error -> {
                LoadResult.Error(Exception(result.error.toString(context)))
            }

            is Result.Success -> {
                LoadResult.Page(
                    data = result.data.podcasts.map { it.toPodcasts() },
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                    nextKey = if (result.data.has_next) currentPage + 1 else null
                )
            }
        }
    }
}