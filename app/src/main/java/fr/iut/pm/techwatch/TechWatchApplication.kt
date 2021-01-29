package fr.iut.pm.techwatch;

import android.app.Application;
import fr.iut.pm.techwatch.db.TechWatchDatabase
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TechWatchApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { TechWatchDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { FeedRepository(database.feedDao(), database.newsDao()) }
}
