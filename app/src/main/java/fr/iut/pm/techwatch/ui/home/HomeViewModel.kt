package fr.iut.pm.techwatch.ui.home

import androidx.lifecycle.*
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import fr.iut.pm.techwatch.db.repositories.NewsRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private var feedRepository: FeedRepository,
    private var newsRepository: NewsRepository,
) : ViewModel() {
    val allFeeds: LiveData<List<Feed>> = feedRepository.findAll()

    fun clearNews() = viewModelScope.launch {
        newsRepository.clearNews()
    }
}

class HomeViewModelFactory(
    private var feedRepository: FeedRepository,
    private var newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(feedRepository, newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}