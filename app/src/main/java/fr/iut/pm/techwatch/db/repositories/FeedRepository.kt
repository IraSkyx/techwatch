package fr.iut.pm.techwatch.db.repositories

import androidx.paging.*
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.dao.FeedDao
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.mediators.NewsRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedRepository(
    private val db: TechWatchDatabase,
    private val feedDao: FeedDao = db.feedDao(),
    private val newsDao: NewsDao = db.newsDao(),
) {
    var dataSource: PagingSource<Int, News>? = null
            
    fun findAll() = feedDao.findAll()

    fun getNewsStream(feed: Feed) = Pager(
        PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        remoteMediator = NewsRemoteMediator(db, feed),
        pagingSourceFactory = {
            dataSource = newsDao.pagingSource(feed.id)
            dataSource!!
        }
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