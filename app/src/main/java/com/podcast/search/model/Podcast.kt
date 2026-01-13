package com.podcast.search.model

import com.google.gson.annotations.SerializedName

data class Podcast(
    @SerializedName("collectionId")
    val id: Long,
    
    @SerializedName("collectionName")
    val title: String?,
    
    @SerializedName("artistName")
    val author: String?,
    
    @SerializedName("artworkUrl600")
    val artworkUrl: String?,
    
    @SerializedName("artworkUrl100")
    val artworkUrlSmall: String?,
    
    @SerializedName("trackCount")
    val episodeCount: Int?,
    
    @SerializedName("feedUrl")
    val feedUrl: String?,
    
    @SerializedName("genres")
    val genres: List<String>?
) {
    fun getImageUrl(): String {
        return artworkUrl ?: artworkUrlSmall ?: ""
    }
}

data class PodcastSearchResponse(
    @SerializedName("resultCount")
    val resultCount: Int,
    
    @SerializedName("results")
    val results: List<Podcast>
)
