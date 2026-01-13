package com.podcast.search.repository

import com.podcast.search.api.RetrofitClient
import com.podcast.search.model.Episode
import com.podcast.search.model.Podcast

class PodcastRepository {
    
    private val apiService = RetrofitClient.apiService
    
    suspend fun searchPodcasts(query: String): Result<List<Podcast>> {
        return try {
            val response = apiService.searchPodcasts(query)
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getEpisodes(podcastId: Long): Result<List<Episode>> {
        return try {
            val response = apiService.getEpisodes(podcastId)
            // First result is the podcast itself, rest are episodes
            val episodes = response.results.filter { it.audioUrl != null }
            Result.success(episodes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getTrending(): Result<List<Podcast>> {
        return try {
            val response = apiService.searchPodcasts("top podcasts")
            Result.success(response.results)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRecentEpisodes(): Result<List<Episode>> {
        return try {
            val response = apiService.searchEpisodes("podcast")
            val episodes = response.results.filter { it.audioUrl != null }
            Result.success(episodes)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
