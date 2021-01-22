package fr.iut.pm.techwatch.repository

import fr.iut.pm.techwatch.dao.FeedDao
import fr.iut.pm.techwatch.entities.Feed
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val IO_EXECUTOR: ExecutorService = Executors.newSingleThreadExecutor()

class FeedRepository(private val feedDao: FeedDao) {
    fun findAll() = IO_EXECUTOR.execute { feedDao.findAll() }

    fun findFeedWithNews(feed: Feed, take: Int, skip: Int) = IO_EXECUTOR.execute {
        val feedWithNews = feedDao.findFeedWithNews(feed.id, take, skip)
        return feedWithNews.size == 0 ? feedAPI() : feedWithNews
    }

    fun upsert(feed: Feed) = IO_EXECUTOR.execute { feedDao.upsert(feed) }

    fun delete(feed: Feed) = IO_EXECUTOR.execute { feedDao.delete(feed) }
}