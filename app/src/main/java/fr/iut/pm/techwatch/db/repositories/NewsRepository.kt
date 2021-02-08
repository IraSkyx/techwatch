package fr.iut.pm.techwatch.db.repositories

import androidx.paging.*
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.mediators.NewsRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepository(
    private val db: TechWatchDatabase,
) {
    var dataSource: PagingSource<Int, News>? = null
            
    fun getNewsStream(feed: Feed) = Pager(
        PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            initialLoadSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        remoteMediator = NewsRemoteMediator(db, feed),
        pagingSourceFactory = {
            dataSource = db.newsDao().pagingSource(feed.id)
            dataSource!!
        }
    ).liveData

    suspend fun clearNews(feed: Feed? = null) = withContext(Dispatchers.IO) {
        if(feed != null) {
            db.newsWithRemoteKeysDao().deleteMany(feed.id)
            db.newsDao().deleteMany(feed.id)
        }
        else {
            db.newsWithRemoteKeysDao().clear()
            db.newsDao().clear()
        }
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}