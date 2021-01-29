package fr.iut.pm.techwatch.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "news",
    foreignKeys = [ForeignKey(entity = Feed::class, parentColumns = ["id"], childColumns = ["feedId"], onDelete = CASCADE)]
)
data class News (
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String,
    var sourceName: String?,
    var authorName: String?,
    var description: String,
    var urlToImage: String?,
    var content: String,
    var publishedAt: String,
    var feedId: Long,
) : Serializable {
}