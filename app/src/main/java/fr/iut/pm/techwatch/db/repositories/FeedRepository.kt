package fr.iut.pm.techwatch.db.repositories

import androidx.lifecycle.liveData
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import fr.iut.pm.techwatch.db.dao.FeedDao
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        var newsFromApi = newsApi.getNews(ServiceBuilder.getEndpoint() + feed.url)
        newsFromApi.articles.forEach { it.feedId = feed.id }

        withContext(Dispatchers.IO) {
            newsDao.upsertMany(*newsFromApi.articles.toTypedArray())
        }

        emitSource(feedDao.findFeedWithNews(feed.id))
    }

    @Suppress("RedundantSuspendModifier")
    suspend fun upsert(feed: Feed) = feedDao.upsert(feed)

    @Suppress("RedundantSuspendModifier")
    suspend fun delete(feed: Feed) = feedDao.delete(feed)
}