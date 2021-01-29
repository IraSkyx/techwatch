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

@Database(entities = [Feed::class, News::class], version = 3, exportSchema = false)
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
                    populateDatabase(database.feedDao(), database.newsDao())
                }
            }
        }

        fun populateDatabase(feedDao: FeedDao, newsDao: NewsDao) {
            feedDao.clear()
            feedDao.upsert(Feed(1, "Feed 1", "everything?q=tesla&pageSize=10&page=1&sortBy=publishedAt&apiKey=7cea05ce4ec74bcd9b3d95a22ecb87c1"))
            feedDao.upsert(Feed(2, "Feed 2", "top-headlines?country=us&pageSize=10&page=1&category=business&apiKey=7cea05ce4ec74bcd9b3d95a22ecb87c1"))

//            newsDao.upsertMany(
//                News(1, "News 1", "", "", "Description 1", "", "", "", 1),
//                News(2, "News 2", "", "", "Description 2", "", "", "", 1),
//                News(3, "News 1", "", "", "Description 1", "", "", "", 2),
//                News(4, "News 2", "", "", "Description 2", "", "", "", 2),
//            )
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