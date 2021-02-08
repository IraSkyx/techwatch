package fr.iut.pm.techwatch.db.services

import fr.iut.pm.techwatch.db.entities.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface NewsService {
    @GET
    suspend fun getNews(
        @Url url: String,
        @Query("page") page: Int? = null,
        @Query("pageSize") pageSize: Int? = null
    ): NewsResponse<List<News>>
}