package com.podcast.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.podcast.search.model.Episode
import com.podcast.search.model.Podcast
import com.podcast.search.repository.PodcastRepository
import kotlinx.coroutines.launch

class PodcastViewModel : ViewModel() {
    
    private val repository = PodcastRepository()
    
    private val _podcasts = MutableLiveData<List<Podcast>>()
    val podcasts: LiveData<List<Podcast>> = _podcasts
    
    private val _episodes = MutableLiveData<List<Episode>>()
    val episodes: LiveData<List<Episode>> = _episodes
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    fun searchPodcasts(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.searchPodcasts(query)
                .onSuccess { _podcasts.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
    
    fun loadTrending() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTrending()
                .onSuccess { _podcasts.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
    
    fun loadRecentEpisodes() {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getRecentEpisodes()
                .onSuccess { _episodes.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
    
    fun getEpisodes(podcastId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getEpisodes(podcastId)
                .onSuccess { _episodes.value = it }
                .onFailure { _error.value = it.message }
            _isLoading.value = false
        }
    }
}
