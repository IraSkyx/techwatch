package fr.iut.pm.techwatch.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.db.repositories.FeedRepository
import kotlinx.coroutines.launch

class UpsertFeedViewModel(
    private val repository: FeedRepository
) : ViewModel() {
    fun upsert(feed: Feed) = viewModelScope.launch {
        repository.upsert(feed)
    }
}

class UpsertFeedViewModelFactory(private val repository: FeedRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpsertFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UpsertFeedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}