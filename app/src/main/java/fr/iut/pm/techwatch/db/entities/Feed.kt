package fr.iut.pm.techwatch.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "feeds")
data class Feed (
    @PrimaryKey(autoGenerate = true) val id: Long = NEW_FEED_ID,
    var name: String,
    var url: String,
) : Serializable {
    companion object {
        const val NEW_FEED_ID = 0L

        @JvmStatic
        fun empty() = Feed(
            name = "",
            url = "",
        )
    }
}