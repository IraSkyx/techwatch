package fr.iut.pm.techwatch.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.adapters.SettingsAdapter
import fr.iut.pm.techwatch.adapters.SwipeToDeleteCallback
import fr.iut.pm.techwatch.db.entities.Feed

class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModels { SettingsViewModelFactory((activity?.application as TechWatchApplication).feedRepository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.settings_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = SettingsAdapter().apply {
            onItemClick = {
                findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToUpsertFeedFragment(it))
            }
        }

        view.findViewById<RecyclerView>(R.id.feeds_recyclerview).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)

            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.allFeeds.value?.get(viewHolder.absoluteAdapterPosition)?.let { feed ->
                        viewModel.delete(feed)
                        listAdapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)

                        Snackbar.make(view, R.string.feed_deleted, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo_action) {
                                viewModel.upsert(feed)
                            }
                            .show()
                    }
                }
            }
            ItemTouchHelper(swipeHandler).attachToRecyclerView(this)
        }

        viewModel.allFeeds.observe(viewLifecycleOwner, {
            listAdapter.submitList(it)
        })

        view.findViewById<FloatingActionButton>(R.id.fab_add_feed).setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToUpsertFeedFragment(Feed.empty()))
        }
    }
}