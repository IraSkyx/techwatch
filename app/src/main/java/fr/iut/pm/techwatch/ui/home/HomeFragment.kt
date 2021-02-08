package fr.iut.pm.techwatch.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.adapters.HomeFeedAdapter

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(
            (activity?.application as TechWatchApplication).feedRepository,
            (activity?.application as TechWatchApplication).newsRepository,
        )
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

        setHasOptionsMenu(true)

        viewModel.allFeeds.observe(viewLifecycleOwner, { feeds ->
            feeds?.let {
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = it.takeIf { position < it.size }?.get(position)?.name
                }.attach()
                viewPager.invalidate()
                listAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                viewModel.clearNews()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}