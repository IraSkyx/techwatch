package fr.iut.pm.techwatch.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "feeds")
data class Feed (
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var url: String,
) : Serializable {
}