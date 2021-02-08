package fr.iut.pm.techwatch.ui.settings

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.databinding.UpsertFeedFragmentBinding
import fr.iut.pm.techwatch.db.entities.Feed

class UpsertFeedFragment : Fragment() {
    private val viewModel: UpsertFeedViewModel by viewModels {
        UpsertFeedViewModelFactory((activity?.application as TechWatchApplication).feedRepository)
    }
    private lateinit var feedBinding: UpsertFeedFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        feedBinding = UpsertFeedFragmentBinding.inflate(inflater)
        feedBinding.feed = arguments?.getSerializable("feed") as Feed
        feedBinding.lifecycleOwner = viewLifecycleOwner

        setHasOptionsMenu(true)

        return feedBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = if (feedBinding.feed?.id == Feed.NEW_FEED_ID) "Add feed" else "Edit feed"
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.upsert_feed_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                feedBinding.feed?.let {
                    viewModel.upsert(it)
                    view?.findNavController()?.navigateUp()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}