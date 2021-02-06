package fr.iut.pm.techwatch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fr.iut.pm.techwatch.db.dao.FeedDao
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Feed::class, News::class], version = 1, exportSchema = false)
abstract class TechWatchDatabase : RoomDatabase() {
    abstract fun feedDao() : FeedDao
    abstract fun newsDao(): NewsDao

    private class TechWatchDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.feedDao())
                }
            }
        }

        fun populateDatabase(feedDao: FeedDao) {
            feedDao.clear()
            feedDao.upsert(Feed(1, "Technology", "top-headlines?country=fr&category=technology&sortBy=publishedAt&apiKey=caabb5f1b6304a30bd7b457ad90f37bb"))
            feedDao.upsert(Feed(2, "Entertainment", "top-headlines?country=fr&category=entertainment&sortBy=publishedAt&apiKey=caabb5f1b6304a30bd7b457ad90f37bb"))
            feedDao.upsert(Feed(3, "Science", "top-headlines?country=fr&category=science&sortBy=publishedAt&apiKey=caabb5f1b6304a30bd7b457ad90f37bb"))
            feedDao.upsert(Feed(4, "Business", "top-headlines?country=fr&category=business&sortBy=publishedAt&apiKey=caabb5f1b6304a30bd7b457ad90f37bb"))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: TechWatchDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): TechWatchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TechWatchDatabase::class.java,
                    "techwatch_database"
                )
                    .addCallback(TechWatchDatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}