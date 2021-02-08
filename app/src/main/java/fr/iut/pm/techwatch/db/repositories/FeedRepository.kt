package fr.iut.pm.techwatch.db.repositories

import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.entities.Feed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepository(
    private val db: TechWatchDatabase,
) {
    fun findAll() = db.feedDao().findAll()

    suspend fun upsert(feed: Feed) = withContext(Dispatchers.IO) {
        db.feedDao().upsert(feed)
        if(feed.id != Feed.NEW_FEED_ID) {
            db.newsWithRemoteKeysDao().deleteMany(feed.id)
            db.newsDao().deleteMany(feed.id)
        }
    }

    suspend fun delete(feed: Feed) = withContext(Dispatchers.IO) {
        db.feedDao().delete(feed)
    }
}