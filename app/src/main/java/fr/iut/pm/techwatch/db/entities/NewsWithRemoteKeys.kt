package fr.iut.pm.techwatch.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsWithRemoteKeys(
    @PrimaryKey
    val newsId: Long,
    val feedId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
)
