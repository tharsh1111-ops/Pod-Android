package com.podcast.search.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.podcast.search.PlayerActivity
import com.podcast.search.databinding.ItemEpisodeBinding
import com.podcast.search.model.Episode // Corrected import
import java.text.SimpleDateFormat
import java.util.Locale

class EpisodeAdapter : ListAdapter<Episode, EpisodeAdapter.EpisodeViewHolder>(EpisodeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EpisodeViewHolder(
        private val binding: ItemEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.apply {
                episodeTitle.text = episode.title ?: "Untitled Episode"
                episodePodcastName.text = episode.podcastName ?: ""
                episodeDuration.text = episode.getDuration()
                
                // Format date
                episode.releaseDate?.let { dateStr ->
                    try {
                        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
                        val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
                        val date = inputFormat.parse(dateStr)
                        episodeDate.text = date?.let { outputFormat.format(it) } ?: ""
                    } catch (e: Exception) {
                        episodeDate.text = ""
                    }
                }
                
                episodeImage.load(episode.getImageUrl()) {
                    crossfade(true)
                    placeholder(android.R.drawable.ic_menu_gallery)
                    error(android.R.drawable.ic_menu_report_image)
                }
                
                listenButton.setOnClickListener {
                    episode.audioUrl?.let { url ->
                        val context = binding.root.context
                        val intent = Intent(context, PlayerActivity::class.java).apply {
                            putExtra("AUDIO_URL", url)
                            putExtra("EPISODE_TITLE", episode.title)
                            putExtra("EPISODE_IMAGE_URL", episode.getImageUrl())
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    class EpisodeDiffCallback : DiffUtil.ItemCallback<Episode>() {
        override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
            return oldItem == newItem
        }
    }
}
