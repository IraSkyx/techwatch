package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeNewsAdapter
import fr.iut.pm.techwatch.adapters.SwipeToDeleteCallback
import fr.iut.pm.techwatch.db.entities.Feed
import kotlinx.coroutines.launch

class FeedFragment(
    private val feed: Feed,
) : Fragment() {
    private val homeFeedViewModel: HomeFeedViewModel by viewModels {
        HomeFeedViewModelFactory((activity?.application as TechWatchApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_feed, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listAdapter = HomeNewsAdapter()

        view.findViewById<RecyclerView>(R.id.home_feed_news).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        homeFeedViewModel.getNews(feed).observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                listAdapter.submitData(it)
            }
        })

        listAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading)
                // Show ProgressBar
            else {
                // Hide ProgressBar

                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(context, it.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }

        listAdapter.onItemClick = {
            val bundle = bundleOf("news" to it)
            view.findNavController().navigate(R.id.newsFragment, bundle)
        }
    }
}