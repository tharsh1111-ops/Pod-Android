package com.podcast.search.api

import com.podcast.search.model.EpisodeSearchResponse
import com.podcast.search.model.PodcastSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface iTunesApiService {
    
    @GET("search")
    suspend fun searchPodcasts(
        @Query("term") query: String,
        @Query("media") media: String = "podcast",
        @Query("entity") entity: String = "podcast",
        @Query("limit") limit: Int = 50
    ): PodcastSearchResponse
    
    @GET("lookup")
    suspend fun getEpisodes(
        @Query("id") podcastId: Long,
        @Query("entity") entity: String = "podcastEpisode",
        @Query("limit") limit: Int = 20
    ): EpisodeSearchResponse
    
    @GET("search")
    suspend fun searchEpisodes(
        @Query("term") query: String,
        @Query("media") media: String = "podcast",
        @Query("entity") entity: String = "podcastEpisode",
        @Query("limit") limit: Int = 20
    ): EpisodeSearchResponse
}
