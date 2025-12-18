package com.johny.mediaverse.presentation.podcast

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.core.presentation.components.EmptyOrErrorScreen
import com.johny.mediaverse.core.presentation.components.ErrorRow
import com.johny.mediaverse.core.presentation.components.LoadingRow
import com.johny.mediaverse.presentation.podcast.components.PodcastItem
import com.johny.mediaverse.presentation.podcast.components.PodcastItemShimmer
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel

@Composable
fun PodcastScreen(
    podcasts: LazyPagingItems<PodcastUIModel>,
    onIntent: (PodcastIntent) -> Unit
) {
    if (podcasts.loadState.refresh is LoadState.Error && podcasts.itemCount == 0) {
        val error = podcasts.loadState.refresh as LoadState.Error
        EmptyOrErrorScreen(
            title = "Error!",
            info = error.error.message
                ?: "Unknown error occurred, try again!",
            primaryLabel = "Retry",
            modifier = Modifier.fillMaxSize()
        ) {
            onIntent(PodcastIntent.RetryPagination)
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingValues(12.dp)),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            when{
                podcasts.loadState.refresh is LoadState.Loading && podcasts.itemCount ==0 ->{
                    items(10) {
                        PodcastItemShimmer()
                        if (it != 9){
                            HorizontalDivider()
                        }
                    }
                }
                else ->{
                    items(
                        count = podcasts.itemCount,
                        key = podcasts.itemKey { it.podcast.id }
                    ) { index ->
                        val podcast = podcasts[index]
                        podcast?.let {
                            PodcastItem(
                                podcastUi = it,
                                onIntent = onIntent
                            )
                            if (index < podcasts.itemCount - 1) {
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }


            podcasts.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            LoadingRow()
                        }
                    }

                    is LoadState.Error -> {
                        val error = podcasts.loadState.append as LoadState.Error
                        item {
                            ErrorRow(
                                message = error.error.message?:"Unknown Error Occurred"
                            ) {

                            }
                        }
                    }
                    else -> {
                        //nothing to do
                    }
                }
            }
        }
    }
}