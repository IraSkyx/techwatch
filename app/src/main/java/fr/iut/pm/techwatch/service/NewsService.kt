package fr.iut.pm.techwatch.service

import fr.iut.pm.techwatch.entities.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {
    @GET("/{params}")
    suspend fun getNews(@Path("params") params: String): List<News>
}