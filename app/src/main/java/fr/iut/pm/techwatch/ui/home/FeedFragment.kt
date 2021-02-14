package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeNewsAdapter
import kotlinx.coroutines.launch

class FeedFragment: Fragment() {
    private val args: FeedFragmentArgs by navArgs()
    private val homeFeedViewModel: HomeFeedViewModel by viewModels { HomeFeedViewModelFactory((activity?.application as TechWatchApplication).newsRepository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.feed_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listAdapter = HomeNewsAdapter().apply {
            onItemClick = {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToNewsFragment(it))
            }
        }

        view.findViewById<RecyclerView>(R.id.home_feed_news).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).apply {
            setOnRefreshListener {
                homeFeedViewModel.invalidateDataSource(args.feed)
                listAdapter.refresh()
                isRefreshing = false
            }
        }

        homeFeedViewModel.getNews(args.feed).observe(viewLifecycleOwner, {
            lifecycleScope.launch {
                listAdapter.submitData(it)
            }
        })
    }
}