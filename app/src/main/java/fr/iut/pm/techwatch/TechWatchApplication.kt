package fr.iut.pm.techwatch

import android.app.Application
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import fr.iut.pm.techwatch.db.repositories.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TechWatchApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { TechWatchDatabase.getDatabase(this, applicationScope) }
    val feedRepository by lazy { FeedRepository(database) }
    val newsRepository by lazy { NewsRepository(database) }
}
