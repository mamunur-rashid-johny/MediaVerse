package com.johny.mediaverse.domain.paging_source

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.johny.mediaverse.core.domain.utils.NetworkError
import com.johny.mediaverse.core.domain.utils.Result
import com.johny.mediaverse.core.presentation.utils.toString
import com.johny.mediaverse.core.utils.Constants.Miscellaneous.STARTING_PAGE_INDEX

/**
 * Generic page-index based [PagingSource] for APIs that return a [Result].
 *
 * Captures the boilerplate shared by every paged feed in the app: starting page
 * index, refresh key calculation, error-to-message mapping and optional
 * de-duplication of items across pages. Only the parts that actually differ are
 * supplied as parameters.
 *
 * @param Dto raw item type returned by the API
 * @param Model domain item type exposed to the UI
 * @param Response full response wrapper returned by the API
 *
 * @param context resolves a [NetworkError] into a user-facing message
 * @param fetch loads a single page from the API
 * @param itemsOf extracts the list of [Dto] out of a [Response]
 * @param hasNextPage whether another page exists after the given [Response]/page
 * @param mapper maps a [Dto] into its domain [Model]
 * @param idSelector stable id used to drop duplicates seen on earlier pages;
 *        pass `null` to keep every item (e.g. APIs without stable ids)
 */
class GenericPagingSource<Dto : Any, Model : Any, Response : Any>(
    private val context: Context,
    private val fetch: suspend (page: Int) -> Result<Response, NetworkError>,
    private val itemsOf: (Response) -> List<Dto>,
    private val hasNextPage: (response: Response, currentPage: Int) -> Boolean,
    private val mapper: (Dto) -> Model,
    private val idSelector: ((Dto) -> Int)? = null,
) : PagingSource<Int, Model>() {

    private val seenIds = mutableSetOf<Int>()

    override fun getRefreshKey(state: PagingState<Int, Model>): Int? {
        seenIds.clear()
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Model> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return when (val result = fetch(currentPage)) {
            is Result.Error -> LoadResult.Error(Exception(result.error.toString(context)))

            is Result.Success -> {
                val items = itemsOf(result.data).let { items ->
                    val selector = idSelector ?: return@let items
                    items.filter { seenIds.add(selector(it)) }
                }
                LoadResult.Page(
                    data = items.map(mapper),
                    prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1,
                    nextKey = if (hasNextPage(result.data, currentPage)) currentPage + 1 else null,
                )
            }
        }
    }
}