package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeNewsAdapter
import fr.iut.pm.techwatch.db.entities.Feed
import kotlinx.coroutines.launch

class FeedFragment: Fragment() {
    private lateinit var feed: Feed

    private val homeFeedViewModel: HomeFeedViewModel by viewModels {
        HomeFeedViewModelFactory((activity?.application as TechWatchApplication).newsRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        feed = arguments?.getSerializable("feed") as Feed
        return inflater.inflate(R.layout.home_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listAdapter = HomeNewsAdapter()

        view.findViewById<RecyclerView>(R.id.home_feed_news).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).apply {
            setOnRefreshListener {
                homeFeedViewModel.invalidateDataSource(feed)
                listAdapter.refresh()
                isRefreshing = false
            }
        }

        homeFeedViewModel.getNews(feed).observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                listAdapter.submitData(it)
            }
        })

        listAdapter.onItemClick = {
            val bundle = bundleOf("news" to it)
            view.findNavController().navigate(R.id.newsFragment, bundle)
        }
    }
}