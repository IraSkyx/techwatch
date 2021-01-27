package fr.iut.pm.techwatch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.iut.pm.techwatch.dao.FeedDao
import fr.iut.pm.techwatch.entities.Feed
import fr.iut.pm.techwatch.entities.News

@Database(entities = [Feed::class, News::class], version = 1)
abstract class FeedDatabase : RoomDatabase() {
    abstract fun feedDao() : FeedDao
}