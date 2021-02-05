package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeNewsAdapter
import fr.iut.pm.techwatch.adapters.SwipeToDeleteCallback
import fr.iut.pm.techwatch.db.entities.Feed

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

        homeFeedViewModel.getFeedWithNews(feed).observe(viewLifecycleOwner, {
            listAdapter.submitList(it.news)
        })

        listAdapter.onItemClick = {
            val bundle = bundleOf("news" to it)
            view.findNavController().navigate(R.id.newsFragment, bundle)
        }
    }
}