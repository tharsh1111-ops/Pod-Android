package com.podcast.search.shared.model

import com.google.gson.annotations.SerializedName

data class Episode(
    @SerializedName("trackId")
    val id: Long,
    
    @SerializedName("trackName")
    val title: String?,
    
    @SerializedName("description")
    val description: String?,
    
    @SerializedName("releaseDate")
    val releaseDate: String?,
    
    @SerializedName("trackTimeMillis")
    val durationMs: Long?,
    
    @SerializedName("episodeUrl")
    val audioUrl: String?,
    
    @SerializedName("collectionName")
    val podcastName: String?,
    
    @SerializedName("artworkUrl600")
    val artworkUrl: String?,
    
    @SerializedName("artworkUrl60")
    val artworkUrlSmall: String?
) {
    fun getImageUrl(): String {
        return artworkUrl ?: artworkUrlSmall ?: ""
    }
    
    fun getDuration(): String {
        if (durationMs == null) return ""
        val seconds = durationMs / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        
        return if (hours > 0) {
            "${hours}h ${minutes % 60}m"
        } else {
            "${minutes}m"
        }
    }
}

data class EpisodeSearchResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    
    @SerializedName("results")
    val results: List<Episode>
)
