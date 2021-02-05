package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeFeedAdapter

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as TechWatchApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        val listAdapter = HomeFeedAdapter(this, viewModel)
        var viewPager = view.findViewById<ViewPager2>(R.id.viewPager).apply {
            adapter = listAdapter
        }

        viewModel.allFeeds.observe(viewLifecycleOwner, { feeds ->
            feeds?.let {
                listAdapter.notifyDataSetChanged()
                viewPager.invalidate();
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = it[position].name
                }.attach()
            }
        })
    }
}