package fr.iut.pm.techwatch.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import fr.iut.pm.techwatch.db.entities.Feed
import fr.iut.pm.techwatch.ui.home.FeedFragment

class HomeFeedAdapter(
    fa: Fragment,
) : FragmentStateAdapter(fa) {
    var feeds: List<Feed> = listOf()

    override fun getItemCount(): Int = feeds.size

    override fun createFragment(position: Int): FeedFragment {
        val fragment = FeedFragment()
        val bundle = Bundle()
        bundle.putSerializable("feed", feeds[position])
        fragment.arguments = bundle
        return fragment
    }
}