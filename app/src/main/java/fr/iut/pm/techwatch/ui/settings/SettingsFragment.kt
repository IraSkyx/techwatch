package fr.iut.pm.techwatch.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.TechWatchApplication
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.adapters.SettingsAdapter

class SettingsFragment : Fragment() {
    private val viewModel: FeedsViewModel by viewModels {
        FeedsViewModelFactory((activity?.application as TechWatchApplication).repository)
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
        }

        viewModel.allFeeds.observe(viewLifecycleOwner, { feeds ->
            feeds?.let { listAdapter.submitList(it) }
        })
    }
}