package fr.iut.pm.techwatch.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FeedWithNews(@Embedded val feed: Feed,
                        @Relation(parentColumn = "id",
                               entityColumn = "feedId")
                           val news: List<News>)