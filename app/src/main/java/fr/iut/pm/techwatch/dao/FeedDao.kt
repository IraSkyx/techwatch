package fr.iut.pm.techwatch.dao

import androidx.room.*
import fr.iut.pm.techwatch.entities.Feed
import fr.iut.pm.techwatch.entities.FeedWithNews

@Dao
interface FeedDao {
    @Query("SELECT * FROM feeds")
    fun findAll(): List<Feed>

    @Transaction
    @Query("SELECT * FROM feeds WHERE id=:id LIMIT :take OFFSET :skip")
    fun findFeedWithNews(id: Long, take: Int, skip: Int): List<FeedWithNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(feed: Feed)

    @Delete
    fun delete(feed: Feed)
}