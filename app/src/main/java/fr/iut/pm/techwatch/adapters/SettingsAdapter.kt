package fr.iut.pm.techwatch.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.R
import fr.iut.pm.techwatch.db.entities.Feed

class SettingsAdapter : ListAdapter<Feed, SettingsAdapter.FeedViewHolder>(FeedsComparator()) {
    var onItemClick : ((feed: Feed) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        return FeedViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, onItemClick)
    }

    class FeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.feeds_recyclerview_item_name)
        private val urlItemView: TextView = itemView.findViewById(R.id.feeds_recyclerview_item_url)

        fun bind(feed: Feed, onItemClick: ((feed: Feed) -> Unit)?) {
            nameItemView.text = feed.name
            urlItemView.text = feed.url
            itemView.setOnClickListener {
                onItemClick?.invoke(feed)
            }
        }

        companion object {
            fun create(parent: ViewGroup): FeedViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.settings_recyclerview_item, parent, false)

                return FeedViewHolder(view)
            }
        }
    }

    class FeedsComparator : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
            return oldItem.name == newItem.name && oldItem.url == newItem.url
        }
    }
}