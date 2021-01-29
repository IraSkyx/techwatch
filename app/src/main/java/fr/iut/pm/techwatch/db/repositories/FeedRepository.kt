package fr.iut.pm.techwatch.db.repositories

import androidx.lifecycle.liveData
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import fr.iut.pm.techwatch.db.dao.FeedDao
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed

class FeedRepository(
    private val feedDao: FeedDao,
    private val newsDao: NewsDao,
) {
    private val newsApi: NewsService = ServiceBuilder.build(NewsService::class.java)

    fun findAll() = feedDao.findAll()

    fun findFeedWithNews(feed: Feed) = liveData {
        //Stale cache
        emitSource(feedDao.findFeedWithNews(feed.id))

        //Revalidate cache
        newsDao.upsertMany(*newsApi.getNews(feed.url).toTypedArray())
        emitSource(feedDao.findFeedWithNews(feed.id))
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun upsert(feed: Feed) = feedDao.upsert(feed)

    @Suppress("RedundantSuspendModifier")
    suspend fun delete(feed: Feed) = feedDao.delete(feed)
}