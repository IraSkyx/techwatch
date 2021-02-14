package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeFeedAdapter

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels { HomeViewModelFactory((activity?.application as TechWatchApplication).feedRepository) }
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = HomeFeedAdapter(this)
        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager).apply {
            adapter = listAdapter
        }

        viewModel.allFeeds.observe(viewLifecycleOwner, { feeds ->
            listAdapter.feeds = feeds

            feeds?.let {
                tabLayoutMediator?.detach()
                tabLayoutMediator = TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = it.takeIf { position < it.size }?.get(position)?.name
                }
                tabLayoutMediator?.attach()
            }
        })
    }
}
