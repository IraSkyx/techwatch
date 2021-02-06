package fr.iut.pm.techwatch.db.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import fr.iut.pm.techwatch.db.dao.FeedDao
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.pagings.NewsPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepository(
    private val feedDao: FeedDao,
    private val newsDao: NewsDao,
) {
    fun findAll() = feedDao.findAll()

    fun getNewsStream(feed: Feed): LiveData<PagingData<News>> = Pager(
        PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = { NewsPagingSource(feed, newsDao) }
    ).liveData

    suspend fun upsert(feed: Feed) = withContext(Dispatchers.IO) {
        feedDao.upsert(feed)
    }

    suspend fun delete(feed: Feed) = withContext(Dispatchers.IO) {
        feedDao.delete(feed)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}