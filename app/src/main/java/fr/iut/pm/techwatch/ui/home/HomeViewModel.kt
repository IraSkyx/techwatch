package fr.iut.pm.techwatch.ui.home

import androidx.lifecycle.*
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.FeedRepository

class HomeViewModel(
    feedRepository: FeedRepository,
) : ViewModel() {
    val allFeeds: LiveData<List<Feed>> = feedRepository.findAll()
}

class HomeViewModelFactory(
    private val feedRepository: FeedRepository,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(feedRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}