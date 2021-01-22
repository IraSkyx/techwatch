package fr.iut.pm.techwatch.api

interface NewsApi {
    @GET("todos/{id}")
    fun getTodo(@Path("id") id: Long): Call<Todo?>?
}