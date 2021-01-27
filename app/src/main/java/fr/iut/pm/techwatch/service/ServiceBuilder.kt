package fr.iut.pm.techwatch.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {
    companion object {
        private var ENDPOINT = "http://newsapi.org/v2/"

        @JvmStatic
        fun <T> build(serviceClass: Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(serviceClass)
        }
    }
}