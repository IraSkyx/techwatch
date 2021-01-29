package fr.iut.pm.techwatch.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.FeedWithNews

@Dao
interface FeedDao {
    @Query("SELECT * FROM feeds")
    fun findAll(): LiveData<List<Feed>>

    @Transaction
    @Query("SELECT * FROM feeds WHERE id=:id")
    fun findFeedWithNews(id: Long): LiveData<FeedWithNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(feed: Feed)

    @Delete
    fun delete(feed: Feed)

    @Query("DELETE FROM feeds")
    fun clear()
}