package fr.iut.pm.techwatch.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.iut.pm.techwatch.db.entities.NewsWithRemoteKeys

@Dao
interface NewsWithRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMany(vararg newsWithRemoteKeys: NewsWithRemoteKeys)

    @Query("SELECT * FROM newsWithRemoteKeys WHERE newsId=:newsId")
    fun findByNewsId(newsId: Long): NewsWithRemoteKeys?

    @Query("DELETE FROM newsWithRemoteKeys WHERE feedId=:feedId")
    fun deleteMany(feedId: Long)

    @Query("DELETE FROM newsWithRemoteKeys")
    fun clear()
}