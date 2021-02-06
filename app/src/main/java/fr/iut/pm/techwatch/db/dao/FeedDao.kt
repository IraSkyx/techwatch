package fr.iut.pm.techwatch.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.iut.pm.techwatch.db.entities.Feed

@Dao
interface FeedDao {
    @Query("SELECT * FROM feeds")
    fun findAll(): LiveData<List<Feed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(feed: Feed)

    @Delete
    fun delete(feed: Feed)

    @Query("DELETE FROM feeds")
    fun clear()
}