package com.johny.mediaverse.data.local.model.podcast

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.johny.mediaverse.utils.toDateFormatString

@Entity(tableName = "podcast")
data class PodcastEntity(
    @PrimaryKey val id: String,
    val publisher: String,
    val thumbnail: String,
    val title: String,
    val totalEpisodes: Int,
    val latestPubDate: Long,
    val country: String,
    val language: String,
    val type: String,
    val audioLengthInSec: Int,
    val savedAt: Long = System.currentTimeMillis()
){
    val savedAtFormattedDate: String
        get() = savedAt.toDateFormatString()
}
