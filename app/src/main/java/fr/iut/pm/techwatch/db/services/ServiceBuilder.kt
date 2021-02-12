package fr.iut.pm.techwatch.db.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ServiceBuilder {
    companion object {
        const val BASE_URL = "https://newsapi.org/"
        const val VERSION = "v2/"

        @JvmStatic
        fun getEndpoint() = BASE_URL + VERSION

        @JvmStatic
        fun <T> build(serviceClass: Class<T>): T {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(serviceClass)
        }
    }
}