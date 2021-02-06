package fr.iut.pm.techwatch.db.pagings

import androidx.paging.PagingSource
import fr.iut.pm.techwatch.db.dao.NewsDao
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.News
import fr.iut.pm.techwatch.db.services.NewsService
import fr.iut.pm.techwatch.db.services.ServiceBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsPagingSource(
    private val feed: Feed,
    private val newsDao: NewsDao,
) : PagingSource<Int, News>() {
    private val newsApi: NewsService = ServiceBuilder.build(NewsService::class.java)

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, News> {
        return try {
            val nextPageNumber = params.key ?: 1

            val response = newsApi.getNews(
                ServiceBuilder.getEndpoint() + feed.url,
                nextPageNumber,
                PAGE_SIZE
            )

            /*withContext(Dispatchers.IO) {
                newsDao.upsertMany(*response.articles.toTypedArray())
            }*/

            return LoadResult.Page(
                data = response.articles,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 10
    }
}