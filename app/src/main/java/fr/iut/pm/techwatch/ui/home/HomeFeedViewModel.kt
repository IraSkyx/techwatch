package fr.iut.pm.techwatch.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.entities.FeedWithNews
import fr.iut.pm.techwatch.db.repositories.FeedRepository

class HomeFeedViewModel(
    private val repository: FeedRepository,
) : ViewModel() {
    fun getFeedWithNews(feed: Feed): LiveData<FeedWithNews> = repository.findFeedWithNews(feed)
}

class HomeFeedViewModelFactory(
    private val repository: FeedRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFeedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}