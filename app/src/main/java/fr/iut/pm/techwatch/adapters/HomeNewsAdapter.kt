package fr.iut.pm.techwatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.databinding.HomeNewsBinding
import fr.iut.pm.techwatch.db.entities.News

class HomeNewsAdapter() : PagingDataAdapter<News, HomeNewsAdapter.NewsViewHolder>(NewsComparator()) {
    var onItemClick : ((news: News) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        if (current != null) {
            holder.bind(current, onItemClick)
        }
    }

    class NewsViewHolder(private var newsBinding: HomeNewsBinding) : RecyclerView.ViewHolder(newsBinding.root) {
        fun bind(news: News, onItemClick: ((news: News) -> Unit)?) {
            newsBinding.news = news

            itemView.findViewById<ImageView>(R.id.home_news_imageView).apply {
                news.urlToImage.let {
                    Picasso.get().load(news.urlToImage).into(this)
                }
            }

            itemView.setOnClickListener {
                onItemClick?.invoke(news)
            }
        }

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                var newsBinding = HomeNewsBinding.inflate(LayoutInflater.from(parent.context))
                return NewsViewHolder(newsBinding)
            }
        }
    }

    class NewsComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean = oldItem == newItem
    }
}