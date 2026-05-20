package com.johny.mediaverse.presentation.tv_show_details

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.config.PosterSize
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvDetailsScreen(
    state: TvShowDetailsState,
    onIntent: (TvShowDetailsIntent) -> Unit
) {
    val scrollState = rememberLazyListState()
    val showCondensedTitle by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex > 0 }
    }
    val isStickyHeaderStuck by remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 ||
                scrollState.firstVisibleItemScrollOffset > 0
        }
    }
    val statusBarTopPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val stickyHeaderTopPadding by animateDpAsState(
        targetValue = if (isStickyHeaderStuck) statusBarTopPadding else 0.dp,
        label = "stickyHeaderTopPadding"
    )
    val safeContentModifier = Modifier.windowInsetsPadding(
        WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        DisposableEffect(isStickyHeaderStuck) {
            val window = (view.context as Activity).window
            val controller = WindowCompat.getInsetsController(window, view)
            val previousAppearance = controller.isAppearanceLightStatusBars
            controller.isAppearanceLightStatusBars = isStickyHeaderStuck
            onDispose {
                controller.isAppearanceLightStatusBars = previousAppearance
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.tvShowDetails == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        } else {
            state.tvShowDetails.let { details ->
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier.fillMaxSize()
                ) {

                    item {
                        BackdropHeaderWithParallax(
                            backdropPath = details.backdropPath,
                            title = details.name,
                            tagline = details.tagline,
                            scrollState = scrollState
                        )
                    }

                    stickyHeader {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(top = stickyHeaderTopPadding),
                            color = MaterialTheme.colorScheme.surface,
                            tonalElevation = 4.dp
                        ) {
                            Column(modifier = safeContentModifier) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    AnimatedVisibility(visible = showCondensedTitle) {
                                        Text(
                                            text = details.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier
                                                .padding(end = 12.dp)
                                                .weight(1f)
                                        )
                                    }
                                    Text(
                                        text = "Seasons (${details.seasons.size})",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.outline,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.weight(1f))
                                    Text(
                                        text = details.networks.firstOrNull()?.name ?: "",
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }

                    item {
                        Column(modifier = safeContentModifier.padding(16.dp)) {
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(details.genres) { genre ->
                                    SuggestionChip(
                                        onClick = { },
                                        label = { Text(genre.name) },
                                        shape = CircleShape
                                    )
                                }
                            }

                            Text(
                                text = details.overview,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 12.dp),
                                lineHeight = 22.sp
                            )
                        }
                    }

                    item {
                        val lastEp = details.lastEpisode
                        Card(
                            modifier = safeContentModifier.padding(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("LAST EPISODE TO AIR", style = MaterialTheme.typography.labelSmall)
                                Text(lastEp.name, style = MaterialTheme.typography.titleLarge)
                                Text(
                                    "Season ${lastEp.seasonNumber}, Episode ${lastEp.episodeNumber}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(
                                    lastEp.overview,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 3,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    itemsIndexed(details.seasons) { index, season ->
                        ListItem(
                            headlineContent = { Text(season.name) },
                            supportingContent = { Text("${season.episodeCount} Episodes • ${season.airDate ?: "N/A"}") },
                            leadingContent = {
                                CoilImage(
                                    modifier = Modifier.size(40.dp, 60.dp),
                                    imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + season.posterPath },
                                    previewPlaceholder = painterResource(R.drawable.image_broken),
                                    imageOptions = ImageOptions(
                                        contentScale = ContentScale.Crop,
                                        alignment = Alignment.Center
                                    ),
                                    failure = {
                                        Image(
                                            painterResource(R.drawable.image_broken),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .padding(8.dp)
                                                .background(
                                                    Color.White.copy(alpha = 0.1f),
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .padding(4.dp),
                                        )
                                    }
                                )
                            },
                            modifier = safeContentModifier.clickable(
                                onClick = {
                                    onIntent(TvShowDetailsIntent.OnNavigateToSeriesDetails(seriesId = details.id, seasonNumber = season.seasonNumber))
                                }
                            )
                        )
                        if (index < details.seasons.lastIndex) {
                            HorizontalDivider(
                                modifier = safeContentModifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }

                    item {
                        Spacer(
                            Modifier
                                .navigationBarsPadding()
                                .height(32.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BackdropHeaderWithParallax(
    backdropPath: String?,
    title: String,
    tagline: String,
    scrollState: LazyListState
) {
    val parallaxOffset by remember {
        derivedStateOf {
            if (scrollState.firstVisibleItemIndex == 0) {
                scrollState.firstVisibleItemScrollOffset * 0.5f
            } else {
                0f
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .clipToBounds()
    ) {
        CoilImage(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = parallaxOffset
                },
            imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + backdropPath },
            previewPlaceholder = painterResource(R.drawable.image_broken),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            failure = {
                Image(
                    painterResource(R.drawable.image_broken),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(4.dp),
                )
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 300f
                    )
                )
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Text(
                text = tagline.uppercase(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}