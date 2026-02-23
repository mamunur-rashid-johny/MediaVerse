package com.johny.mediaverse.presentation.movie_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.johny.mediaverse.R
import com.johny.mediaverse.core.utils.Constants
import com.johny.mediaverse.domain.config.PosterSize
import com.johny.mediaverse.domain.model.movie_details.GenreModel
import com.johny.mediaverse.domain.model.movie_details.MovieDetailsModel
import com.johny.mediaverse.domain.model.movie_details.ProductionCompanyModel
import com.johny.mediaverse.utils.formatCurrency
import com.johny.mediaverse.utils.formatRuntime
import com.johny.mediaverse.utils.shimmerEffect
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import java.util.Locale

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    state: MovieDetailsState
) {
    if (state.movieDetails != null && !state.isLoading){
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                MovieHeader(state.movieDetails)

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = state.movieDetails.title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (state.movieDetails.tagline.isNotBlank()) {
                        Text(
                            text = "\"${state.movieDetails.tagline}\"",
                            style = MaterialTheme.typography.titleMedium,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    MovieMetaRow(state.movieDetails)

                    Spacer(modifier = Modifier.height(16.dp))

                    GenreList(state.movieDetails.genres)

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 24.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    SectionTitle("Overview")
                    Text(
                        text = state.movieDetails.overview,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    FinancialsSection(
                        budget = state.movieDetails.budget.toLong(),
                        revenue = state.movieDetails.revenue.toLong()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    SectionTitle("Production")

                    ProductionList(state.movieDetails.productionCompanies)

                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
        }
    }
}

@Composable
fun MovieHeader(movieDetails: MovieDetailsModel) {
    Box(
        modifier = Modifier
            .height(380.dp)
            .fillMaxWidth()
    ) {
        CoilImage(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth(),
            imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + movieDetails.backdropPath },
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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                }
            }
        )

        Box(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                        startY = 100f
                    )
                )
        )

        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp)
                .offset(y = 0.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(140.dp)
                    .aspectRatio(2f / 3f)
            ) {
                CoilImage(
                    modifier = Modifier.fillMaxSize(),
                    imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + movieDetails.posterPath },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    failure = {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmerEffect()
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun MovieMetaRow(movieDetails: MovieDetailsModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MetaItem(icon = Icons.Default.CalendarToday, text = movieDetails.releaseDate.take(4))
        MetaItem(icon = Icons.Default.AccessTime, text = formatRuntime(movieDetails.runtime))
        MetaItem(
            icon = Icons.Default.Star,
            text = String.format(Locale.getDefault(), "%.1f/10", movieDetails.voteAverage),
            iconColor = Color(0xFFFFC107)
        )
    }
}

@Composable
fun MetaItem(icon: ImageVector, text: String, iconColor: Color = MaterialTheme.colorScheme.secondary) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun GenreList(genres: List<GenreModel>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(genres) { genre ->
            SuggestionChip(
                onClick = { },
                label = { Text(genre.name) },
                shape = CircleShape
            )
        }
    }
}

@Composable
fun FinancialsSection(budget: Long, revenue: Long) {
    Row(modifier = Modifier.fillMaxWidth()) {
        FinancialItem(
            label = "Budget",
            amount = formatCurrency(budget),
            modifier = Modifier.weight(1f)
        )
        FinancialItem(
            label = "Revenue",
            amount = formatCurrency(revenue),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun FinancialItem(label: String, amount: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ProductionList(companies: List<ProductionCompanyModel>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(companies) { company ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(80.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    imageModel = { Constants.MovieDbUrl.IMAGE_ROOT_PATH + PosterSize.W500.value + company.logoPath },
                    previewPlaceholder = painterResource(R.drawable.cinema_studio),
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    ),
                    failure = {
                        Image(
                            painterResource(R.drawable.cinema_studio),
                            contentDescription = null,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(8.dp)
                                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                                .padding(4.dp),
                        )
                    }
                )



                Text(
                    text = company.name,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}