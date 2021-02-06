package fr.iut.pm.techwatch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import kotlinx.coroutines.launch

class HomeFeedViewModel(
    private val repository: FeedRepository,
) : ViewModel() {
    fun getNews(feed: Feed) =
        repository
        .getNewsStream(feed)
        .cachedIn(viewModelScope)

    fun invalidateDataSource() = repository.dataSource?.invalidate()
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