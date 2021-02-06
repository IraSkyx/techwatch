package fr.iut.pm.techwatch.db.mediators

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.lang.IllegalArgumentException

class NewsRemoteMediator(
    private val db: TechWatchDatabase,
    private val feed: Feed,
    private val newsDao: NewsDao = db.newsDao(),
) : RemoteMediator<Int, News>() {
    private val newsApi: NewsService = ServiceBuilder.build(NewsService::class.java)
    private var nextPageNumber : Int = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, News>
    ): MediatorResult {
        return try {
            nextPageNumber = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.APPEND -> nextPageNumber + 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            }

            withContext(Dispatchers.IO) {
                val response = newsApi.getNews(
                    ServiceBuilder.getEndpoint() + feed.url,
                    nextPageNumber,
                    state.config.pageSize
                ).execute()

                if(!response.isSuccessful) {
                    throw HttpException(response)
                }

                val body = response.body()!!

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        newsDao.deleteMany(feed.id)
                    }
                    body.articles.forEach { it.feedId = feed.id }
                    newsDao.upsertMany(*body.articles.toTypedArray())
                }

                MediatorResult.Success(
                    endOfPaginationReached = body.articles.size < state.config.pageSize
                )
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}