package com.johny.mediaverse.presentation.trending

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
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

/**
 * Created by Johny on 27/5/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TrendingScreen(
    trending: LazyPagingItems<TrendingUiModel>,
    onIntent: (TrendingIntent) -> Unit
) {

    when {
        trending.loadState.refresh is LoadState.Error && trending.itemCount == 0 -> {
            val error = trending.loadState.refresh as LoadState.Error
            EmptyOrErrorScreen(
                title = "Error!",
                info = error.error.message
                    ?: "Unknown error occurred, try again!",
                primaryLabel = "Retry",
                modifier = Modifier.fillMaxSize()
            ) {
                onIntent(TrendingIntent.RetryPaginationIntent)
            }
        }

        trending.loadState.refresh is LoadState.Loading && trending.itemCount == 0 -> {
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
                        AnimatedGradientText(text = "Trending")
                    }
                }
                items(
                    count = trending.itemCount,
                    key = trending.itemKey { it.trending.id }
                ) { index ->
                    val item = trending[index]
                    item?.let {
                        TrendingItem(
                            model = item,
                            onIntent = onIntent
                        )
                    }
                }

                trending.apply {
                    when (loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                LoadingRow()
                            }
                        }

                        is LoadState.Error -> {
                            val error = trending.loadState.append as LoadState.Error
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