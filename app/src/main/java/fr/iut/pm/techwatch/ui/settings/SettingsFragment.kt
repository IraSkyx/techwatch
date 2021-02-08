package fr.iut.pm.techwatch.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
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
    private val viewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((activity?.application as TechWatchApplication).feedRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = SettingsAdapter()

        view.findViewById<RecyclerView>(R.id.feeds_recyclerview).apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)

            val swipeHandler = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    viewModel.allFeeds.value?.get(viewHolder.adapterPosition)?.let { feed ->
                        viewModel.delete(feed)
                        listAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                        Snackbar.make(view, R.string.feed_deleted, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo_action) {
                                viewModel.upsert(feed)
                            }
                            .show()
                    }
                }
            }
            ItemTouchHelper(swipeHandler).attachToRecyclerView(this)

            listAdapter.onItemClick = {
                val bundle = bundleOf("feed" to it)
                view.findNavController().navigate(R.id.upsertFeedFragment, bundle)
            }
        }

        viewModel.allFeeds.observe(viewLifecycleOwner, { feeds ->
            feeds?.let { listAdapter.submitList(it) }
        })

        view.findViewById<FloatingActionButton>(R.id.fab_add_feed).setOnClickListener {
            val bundle = bundleOf("feed" to Feed.empty())
            view.findNavController().navigate(R.id.upsertFeedFragment, bundle)
        }
    }
}