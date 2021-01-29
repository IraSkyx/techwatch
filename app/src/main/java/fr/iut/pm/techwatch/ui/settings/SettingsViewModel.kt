package fr.iut.pm.techwatch.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import kotlinx.coroutines.launch

class FeedsViewModel(
    private val repository: FeedRepository
) : ViewModel() {
    val allFeeds: LiveData<List<Feed>> = repository.findAll()

    fun upsert(feed: Feed) = viewModelScope.launch {
        repository.upsert(feed)
    }

    fun delete(feed: Feed) = viewModelScope.launch {
        repository.delete(feed)
    }
}

class FeedsViewModelFactory(private val repository: FeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}