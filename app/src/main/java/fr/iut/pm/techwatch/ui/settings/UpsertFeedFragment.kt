package fr.iut.pm.techwatch.ui.settings

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.databinding.UpsertFeedFragmentBinding
import fr.iut.pm.techwatch.db.entities.Feed

class UpsertFeedFragment : Fragment() {
    private val args: UpsertFeedFragmentArgs by navArgs()
    private val viewModel: UpsertFeedViewModel by viewModels { UpsertFeedViewModelFactory((activity?.application as TechWatchApplication).feedRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as AppCompatActivity).supportActionBar?.title = if (args.feed.id == Feed.NEW_FEED_ID) resources.getString(R.string.add_feed) else resources.getString(R.string.edit_feed)
        setHasOptionsMenu(true)

        return UpsertFeedFragmentBinding.inflate(inflater).apply {
            feed = args.feed
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.upsert_feed_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                args.feed.let {
                    viewModel.upsert(it)
                    view?.findNavController()?.navigateUp()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}