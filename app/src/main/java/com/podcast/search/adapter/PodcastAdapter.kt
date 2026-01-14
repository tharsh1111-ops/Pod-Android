package com.podcast.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.podcast.search.databinding.ItemPodcastBinding
import com.podcast.search.model.Podcast // Corrected import

class PodcastAdapter(
    private val onPodcastClick: (Podcast) -> Unit
) : ListAdapter<Podcast, PodcastAdapter.PodcastViewHolder>(PodcastDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PodcastViewHolder {
        val binding = ItemPodcastBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PodcastViewHolder(binding, onPodcastClick)
    }

    override fun onBindViewHolder(holder: PodcastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PodcastViewHolder(
        private val binding: ItemPodcastBinding,
        private val onPodcastClick: (Podcast) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(podcast: Podcast) {
            binding.apply {
                podcastTitle.text = podcast.title ?: "Untitled"
                podcastAuthor.text = podcast.author ?: "Unknown"
                podcastEpisodeCount.text = "${podcast.episodeCount ?: 0} episodes"
                
                podcastImage.load(podcast.getImageUrl()) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_menu_report_image)
                }
                
                viewEpisodesButton.setOnClickListener {
                    onPodcastClick(podcast)
                }
            }
        }
    }

    class PodcastDiffCallback : DiffUtil.ItemCallback<Podcast>() {
        override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean {
            return oldItem == newItem
        }
    }
}
