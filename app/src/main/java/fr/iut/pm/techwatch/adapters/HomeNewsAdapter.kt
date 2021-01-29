package fr.iut.pm.techwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.db.entities.News

class HomeNewsAdapter : ListAdapter<News, HomeNewsAdapter.NewsViewHolder>(NewsComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleItemView: TextView = itemView.findViewById(R.id.home_news_title)
        private val descriptionItemView: TextView = itemView.findViewById(R.id.home_news_description)

        fun bind(news: News) {
            titleItemView.text = news.title
            descriptionItemView.text = news.description
        }

        companion object {
            fun create(parent: ViewGroup): NewsViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.home_news, parent, false)
                return NewsViewHolder(view)
            }
        }
    }

    class NewsComparator : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.title == newItem.title && oldItem.publishedAt == newItem.publishedAt
        }
    }
}