package com.johny.mediaverse.presentation.podcast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.johny.mediaverse.core.navigation.LocalScaffoldPadding
import com.johny.mediaverse.core.presentation.components.AnimatedGradientText
import com.johny.mediaverse.core.presentation.components.EmptyOrErrorScreen
import com.johny.mediaverse.core.presentation.components.ErrorRow
import com.johny.mediaverse.core.presentation.components.LoadingRow
import com.johny.mediaverse.presentation.podcast.components.PodcastItem
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PodcastScreen(
    podcasts: LazyPagingItems<PodcastUIModel>,
    onIntent: (PodcastIntent) -> Unit
) {

    when {
        podcasts.loadState.refresh is LoadState.Error && podcasts.itemCount == 0 -> {
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
        }

        podcasts.loadState.refresh is LoadState.Loading && podcasts.itemCount == 0 -> {
            Box(modifier = Modifier.fillMaxSize()) {
                LoadingIndicator(
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        else -> {
            val scaffoldPadding = LocalScaffoldPadding.current
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = scaffoldPadding.calculateTopPadding() + 20.dp,
                    bottom = scaffoldPadding.calculateBottomPadding() + 20.dp
                )
            ) {

                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AnimatedGradientText(text = "Discover Podcasts")
                    }
                }
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
                                    message = error.error.message ?: "Unknown Error Occurred"
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
}