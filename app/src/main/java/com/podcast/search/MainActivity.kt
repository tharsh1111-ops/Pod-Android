package com.podcast.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.podcast.search.adapter.EpisodeAdapter
import com.podcast.search.adapter.PodcastAdapter
import com.podcast.search.databinding.ActivityMainBinding
import com.podcast.search.model.Podcast
import com.podcast.search.viewmodel.PodcastViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PodcastViewModel by viewModels()
    private lateinit var podcastAdapter: PodcastAdapter
    private lateinit var episodeAdapter: EpisodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerViews()
        setupListeners()
        observeViewModel()

        // Load trending podcasts on start
        viewModel.loadTrending()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerViews() {
        // Podcasts RecyclerView
        podcastAdapter = PodcastAdapter { podcast: Podcast ->
            viewModel.getEpisodes(podcast.id)
            binding.resultsTitle.text = podcast.title ?: "Episodes"
        }
        binding.podcastsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = podcastAdapter
        }

        // Episodes RecyclerView
        episodeAdapter = EpisodeAdapter()
        binding.episodesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = episodeAdapter
        }
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            performSearch()
        }

        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.trendingButton.setOnClickListener {
            binding.resultsTitle.text = getString(R.string.trending_podcasts)
            viewModel.loadTrending()
        }

        binding.recentButton.setOnClickListener {
            binding.resultsTitle.text = getString(R.string.recent_episodes)
            viewModel.loadRecentEpisodes()
        }
    }

    private fun performSearch() {
        val query = binding.searchEditText.text.toString().trim()
        if (query.isNotEmpty()) {
            binding.resultsTitle.text = "Search: \"$query\""
            viewModel.searchPodcasts(query)
        }
    }

    private fun observeViewModel() {
        viewModel.podcasts.observe(this) { podcasts ->
            podcastAdapter.submitList(podcasts)
            binding.podcastsRecyclerView.visibility = View.VISIBLE
            binding.episodesRecyclerView.visibility = View.GONE
            binding.resultsTitle.visibility = if (podcasts.isNotEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.episodes.observe(this) { episodes ->
            episodeAdapter.submitList(episodes)
            binding.episodesRecyclerView.visibility = View.VISIBLE
            binding.podcastsRecyclerView.visibility = View.GONE
            binding.resultsTitle.visibility = if (episodes.isNotEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { error ->
            error?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                binding.errorText.text = it
                binding.errorText.visibility = View.VISIBLE
            }
        }
    }
}