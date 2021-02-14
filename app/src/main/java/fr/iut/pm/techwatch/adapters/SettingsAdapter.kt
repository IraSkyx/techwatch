package fr.iut.pm.techwatch.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.iut.pm.techwatch.databinding.SettingsRecyclerviewItemBinding
import fr.iut.pm.techwatch.db.entities.Feed

class SettingsAdapter : ListAdapter<Feed, SettingsAdapter.FeedViewHolder>(FeedsComparator()) {
    var onItemClick : ((feed: Feed) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeedViewHolder.create(parent)

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) = holder.bind(getItem(position), onItemClick)

    class FeedViewHolder(private val binding: SettingsRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(feed: Feed, onItemClick: ((feed: Feed) -> Unit)?) {
            binding.feed = feed
            itemView.setOnClickListener {
                onItemClick?.invoke(feed)
            }
        }

        companion object {
            fun create(parent: ViewGroup) = FeedViewHolder(SettingsRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    class FeedsComparator : DiffUtil.ItemCallback<Feed>() {
        override fun areItemsTheSame(oldItem: Feed, newItem: Feed) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Feed, newItem: Feed) = oldItem.name == newItem.name && oldItem.url == newItem.url
    }
}