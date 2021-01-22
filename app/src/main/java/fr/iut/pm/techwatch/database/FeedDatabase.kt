package fr.iut.pm.techwatch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.iut.pm.techwatch.dao.FeedDao
import fr.iut.pm.techwatch.entities.Feed

@Database(entities = [Feed::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedDAO() : FeedDao
}