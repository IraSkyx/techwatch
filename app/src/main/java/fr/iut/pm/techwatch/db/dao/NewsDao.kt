package fr.iut.pm.techwatch.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.iut.pm.techwatch.db.entities.News

@Dao
interface NewsDao {
    @Query("SELECT * FROM news WHERE id=:id")
    fun findUnique(id: Long): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertMany(vararg news: News)

    @Query("DELETE from news where feedId=:feedId")
    fun deleteMany(feedId: Long)
}