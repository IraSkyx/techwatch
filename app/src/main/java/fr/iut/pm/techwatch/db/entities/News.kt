package fr.iut.pm.techwatch.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(
    tableName = "news",
    foreignKeys = [ForeignKey(entity = Feed::class, parentColumns = ["id"], childColumns = ["feedId"], onDelete = CASCADE)]
)
data class News (
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    var sourceName: String?,
    var authorName: String?,
    var description: String?,
    var urlToImage: String?,
    var content: String?,
    var publishedAt: Date?,
    @ColumnInfo(index = true) var feedId: Long,
) : Serializable {
}