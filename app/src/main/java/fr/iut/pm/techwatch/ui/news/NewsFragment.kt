package fr.iut.pm.techwatch.ui.news

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.databinding.NewsFragmentBinding
import fr.iut.pm.techwatch.db.entities.News

class NewsFragment : Fragment() {
    private lateinit var news: News

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var newsBinding = NewsFragmentBinding.inflate(inflater)
        news = arguments?.getSerializable("news") as News
        newsBinding.news = news
        newsBinding.lifecycleOwner = viewLifecycleOwner

        return newsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = news.title

        view.findViewById<ImageView>(R.id.newsFragment_imageView).apply {
            news.urlToImage.let {
                Picasso.get().load(news.urlToImage).into(this)
            }
        }
    }
}