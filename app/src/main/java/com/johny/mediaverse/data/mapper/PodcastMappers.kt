package com.johny.mediaverse.data.mapper

import com.johny.mediaverse.data.local.model.podcast.PodcastEntity
import com.johny.mediaverse.data.model.podcast.PodcastDto
import com.johny.mediaverse.data.model.podcast.PodcastResponseDto
import com.johny.mediaverse.data.model.podcast_details.EpisodeDto
import com.johny.mediaverse.data.model.podcast_details.PodcastDetailsDto
import com.johny.mediaverse.domain.model.podcast.Podcast
import com.johny.mediaverse.domain.model.podcast.PodcastResponse
import com.johny.mediaverse.domain.model.podcast_details.EpisodeModel
import com.johny.mediaverse.domain.model.podcast_details.PodcastDetails
import com.johny.mediaverse.domain.model.podcast_details.PodcastHeaderDetails
import com.johny.mediaverse.presentation.podcast.ui_model.PodcastUIModel

fun PodcastResponseDto.toPodcast(): PodcastResponse {
    return PodcastResponse(
        hasNext = this.has_next,
        hasPrevious = this.has_previous,
        nextPageNumber = this.next_page_number,
        pageNumber = this.page_number,
        previousPageNumber = this.previous_page_number,
        total = this.total,
        podcasts = this.podcasts.map { it.toPodcasts() }
    )
}


fun PodcastDto.toPodcasts(): Podcast {
    return Podcast(
        id = this.id,
        publisher = this.publisher,
        thumbnail = this.thumbnail,
        title = this.title,
        totalEpisodes = this.total_episodes,
        latestPubDate = this.latest_pub_date_ms,
        country = this.country,
        language = this.language,
        type = this.type,
        audioLengthInSec = this.audio_length_sec
    )
}

fun PodcastDetailsDto.toPodcastDetails(): PodcastDetails{
    return PodcastDetails(
        id = this.id,
        title = this.title,
        publisher = this.publisher,
        thumbnail = this.thumbnail,
        totalEpisodes = this.total_episodes,
        description = this.description,
        language = this.language,
        country = this.country,
        website = this.website,
        nextEpisodePubDate = this.next_episode_pub_date,
        episodes = this.episodes.map { it.toEpisodeModel() }
    )
}

fun EpisodeDto.toEpisodeModel(): EpisodeModel{
    return EpisodeModel(
        audio = this.audio,
        audioLengthSec = this.audio_length_sec,
        description = this.description,
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail,
        pubDate = this.pub_date_ms
    )
}

fun List<EpisodeModel>.playLatestEpisode(): EpisodeModel? {
    return this.firstOrNull()
}

fun PodcastDetails.toPodcastHeaderDetails(): PodcastHeaderDetails {
    return PodcastHeaderDetails(
        id = this.id,
        title = this.title,
        publisher = this.publisher,
        thumbnail = this.thumbnail,
        totalEpisodes = this.totalEpisodes,
        description = this.description,
        language = this.language,
        country = this.country,
        website = this.website,
        nextEpisodePubDate = this.nextEpisodePubDate,
        latestEpisode = this.episodes.playLatestEpisode()
    )
}

fun PodcastEntity.toPodcast(): Podcast{
    return Podcast(
        id = this.id,
        publisher = this.publisher,
        thumbnail = this.thumbnail,
        title = this.title,
        totalEpisodes = this.totalEpisodes,
        latestPubDate = this.latestPubDate,
        country = this.country,
        language = this.language,
        type = this.type,
        audioLengthInSec = this.audioLengthInSec
    )
}

fun Podcast.toPodcastEntity(): PodcastEntity{
    return PodcastEntity(
        id = this.id,
        publisher = this.publisher,
        thumbnail = this.thumbnail,
        title = this.title,
        totalEpisodes = this.totalEpisodes,
        latestPubDate = this.latestPubDate,
        country = this.country,
        language = this.language,
        type = this.type,
        audioLengthInSec = this.audioLengthInSec
    )
}

fun PodcastEntity.toPodcastUiModel(): PodcastUIModel{
    return PodcastUIModel(
        podcast = this.toPodcast(),
        isBookmark = true
    )
}
