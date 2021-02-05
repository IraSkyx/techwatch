package fr.iut.pm.techwatch.ui.news

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import fr.iut.pm.techwatch.databinding.NewsFragmentBinding
import fr.iut.pm.techwatch.db.entities.News

class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var newsBinding = NewsFragmentBinding.inflate(inflater)
        newsBinding.news = arguments?.getSerializable("news") as News
        newsBinding.lifecycleOwner = viewLifecycleOwner

        return newsBinding.root
    }
}