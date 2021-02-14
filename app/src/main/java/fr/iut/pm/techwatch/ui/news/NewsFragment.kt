package fr.iut.pm.techwatch.ui.news

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.databinding.NewsFragmentBinding

class NewsFragment : Fragment() {
    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = NewsFragmentBinding.inflate(inflater).apply {
        news = args.news
        lifecycleOwner = viewLifecycleOwner
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = args.news.title

        view.findViewById<ImageView>(R.id.newsFragment_imageView).apply {
            args.news.description.let { contentDescription = it }
            args.news.urlToImage.let { Picasso.get().load(args.news.urlToImage).into(this) }
        }
    }
}