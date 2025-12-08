package com.johny.mediaverse.data.local.model.tv_show

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.johny.mediaverse.utils.DateFormat
import com.johny.mediaverse.utils.toDateFormatString

@Entity(tableName = "tv_show")
data class TvShowEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val rating: Double,
    val releaseDate: String,
    val posterPath: String,
    val savedAt: Long = System.currentTimeMillis()
){
    val savedAtFormattedDate: String
        get() = savedAt.toDateFormatString(DateFormat.MMM_DD_YYYY_STRING_HH_MM_A)
}
