package fr.iut.pm.techwatch.db.mediators

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.entities.NewsWithRemoteKeys
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InvalidObjectException

class NewsRemoteMediator(
    private val db: TechWatchDatabase,
    private val feed: Feed,
    private val initialPage: Int = 1,
    private val newsApi: NewsService = ServiceBuilder.build(NewsService::class.java)
) : RemoteMediator<Int, News>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, News>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: initialPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state) ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            withContext(Dispatchers.IO) {
                val response = newsApi.getNews(
                    ServiceBuilder.getEndpoint() + feed.url,
                    page,
                    state.config.pageSize
                )

                val endOfPaginationReached = response.articles.size < state.config.pageSize

                db.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        db.newsWithRemoteKeysDao().deleteMany(feed.id)
                        db.newsDao().deleteMany(feed.id)
                    }

                    val prevKey = if (page == initialPage) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    response.articles.forEach { it.feedId = feed.id }
                    val insertedIds = db.newsDao().upsertMany(*response.articles.toTypedArray())

                    val keys = insertedIds.map {
                        NewsWithRemoteKeys(newsId = it, feedId = feed.id, prevKey = prevKey, nextKey = nextKey)
                    }
                    db.newsWithRemoteKeysDao().upsertMany(*keys.toTypedArray())
                }

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, News>): NewsWithRemoteKeys? {
        return state.lastItemOrNull()?.let { news ->
            db.withTransaction { db.newsWithRemoteKeysDao().findByNewsId(news.id) }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, News>): NewsWithRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.withTransaction { db.newsWithRemoteKeysDao().findByNewsId(id) }
            }
        }
    }
}