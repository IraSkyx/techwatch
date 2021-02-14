package fr.iut.pm.techwatch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.NewsRepository
import kotlinx.coroutines.launch

class HomeFeedViewModel(
    private val repository: NewsRepository,
) : ViewModel() {
    fun getNews(feed: Feed) =
        repository
        .getNewsStream(feed)
        .cachedIn(viewModelScope)

    fun invalidateDataSource(feed: Feed) = viewModelScope.launch {
        repository.clearNews(feed)
        repository.dataSource?.invalidate()
    }
}

class HomeFeedViewModelFactory(
    private val repository: NewsRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeFeedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}