package fr.iut.pm.techwatch.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import fr.iut.pm.techwatch.db.entities.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE id=:id")
    fun findUnique(id: Long): LiveData<News>

    @Query("SELECT * FROM news WHERE feedId=:id")
    fun pagingSource(id: Long): PagingSource<Int, News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMany(vararg news: News) : List<Long>

    @Query("DELETE from news where feedId=:feedId")
    fun deleteMany(feedId: Long)

    @Query("DELETE FROM news")
    fun clear()
}