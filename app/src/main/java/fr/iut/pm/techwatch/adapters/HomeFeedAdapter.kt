package fr.iut.pm.techwatch.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.iut.pm.techwatch.ui.home.FeedFragment
import fr.iut.pm.techwatch.ui.home.HomeViewModel

class HomeFeedAdapter(
    fa: Fragment,
    private val viewModel: HomeViewModel,
) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = viewModel.allFeeds.value?.size ?: 0 //TODO Improve update when LiveData change

    override fun createFragment(position: Int): Fragment = FeedFragment(viewModel.allFeeds.value?.get(position)!!)
}