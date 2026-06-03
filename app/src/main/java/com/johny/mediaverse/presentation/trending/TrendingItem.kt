package com.johny.mediaverse.presentation.trending

/**
 * Created by Johny on 4/6/26.
 * Copyright (c) 2026 Pathao Ltd. All rights reserved.
 */

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.config.PosterSize
import com.johny.mediaverse.domain.model.trending.TrendingModel
import com.johny.mediaverse.utils.shimmerEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingItem(
    model: TrendingModel,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onClick(model.id) },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CoilImage(
                modifier = Modifier.fillMaxSize(),
                imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + model.backdropPath },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                ),
                previewPlaceholder = painterResource(R.drawable.movie_tv_placeholder),
                failure = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect())
                    }
                }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f),
                                Color.Black.copy(alpha = 0.9f)
                            ),
                            startY = 100f
                        )
                    )
            )

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.7f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "⭐ ",
                        fontSize = 12.sp
                    )
                    Text(
                        text = String.format(Locale.US,"%.1f", model.voteAverage),
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }

            // 4. Content Details (Bottom)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = model.title,
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = model.releaseDate.take(4), // Extracts Year
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "•",
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )
                    Text(
                        text = model.mediaType.uppercase(),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = model.overview,
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
            }
        }
    }
}