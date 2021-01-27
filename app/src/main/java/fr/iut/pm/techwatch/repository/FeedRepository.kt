package fr.iut.pm.techwatch.repository

import androidx.lifecycle.liveData
import fr.iut.pm.techwatch.service.NewsService
import fr.iut.pm.techwatch.service.ServiceBuilder
import fr.iut.pm.techwatch.dao.FeedDao
import fr.iut.pm.techwatch.dao.NewsDao
import fr.iut.pm.techwatch.entities.Feed
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

val IO_EXECUTOR: ExecutorService = Executors.newSingleThreadExecutor()

class FeedRepository(
    private val feedDao: FeedDao,
    private val newsDao: NewsDao,
    private val newsApi: NewsService = ServiceBuilder.build(NewsService::class.java)
) {
    fun findAll() = feedDao.findAll()

    fun findFeedWithNews(feed: Feed, take: Int, skip: Int) = liveData(Dispatchers.IO) {
        //Retrieve news from cache first
        var news = feedDao.findFeedWithNews(feed.id, take, skip)

        //If no cache is found
        if(news.value?.size == 0)  {
            //Retrieve it from API
            var apiNews = newsApi.getNews(String.format("?page=%d&pageSize=%d", skip/take, take))

            // Link it to the corresponding feed
            var newsConnectedToFeed = apiNews.map {
                it.feedId = feed.id
                return@map it
            }

            //Insert new cache into database
            newsDao.upsertMany(*newsConnectedToFeed.toTypedArray())
        }
        else {
            emit(news.value)
        }
    }

    fun upsert(feed: Feed) = IO_EXECUTOR.execute { feedDao.upsert(feed) }

    fun delete(feed: Feed) = IO_EXECUTOR.execute { feedDao.delete(feed) }
}