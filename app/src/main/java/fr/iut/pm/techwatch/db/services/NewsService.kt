package fr.iut.pm.techwatch.db.services

import fr.iut.pm.techwatch.db.entities.News
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("/v2/{params}")
    suspend fun getNews(@Path("params") params: String): List<News>
}