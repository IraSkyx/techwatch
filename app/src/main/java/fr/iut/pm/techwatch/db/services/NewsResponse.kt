package fr.iut.pm.techwatch.db.services

class NewsResponse<T>(
    val status: String,
    val totalResults: Int,
    val articles: T,
)
