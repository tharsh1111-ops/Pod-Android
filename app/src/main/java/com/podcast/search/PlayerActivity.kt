package com.podcast.search

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import coil.ImageLoader
import coil.request.ImageRequest
import com.podcast.search.databinding.ActivityPlayerBinding
import java.io.ByteArrayOutputStream

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var player: ExoPlayer? = null
    private var mediaSession: MediaSession? = null
    private var playbackPosition: Long = 0L

    companion object {
        private const val KEY_PLAYBACK_POSITION = "playback_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION, 0L)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        player?.let {
            outState.putLong(KEY_PLAYBACK_POSITION, it.currentPosition)
        }
    }

    override fun onStart() {
        super.onStart()
        loadArtworkAndInitializePlayer()
    }

    private fun loadArtworkAndInitializePlayer() {
        val episodeImageUrl = intent.getStringExtra("EPISODE_IMAGE_URL")
        val imageLoader = ImageLoader(this)
        val request = ImageRequest.Builder(this)
            .data(episodeImageUrl)
            .target(
                onSuccess = { result ->
                    val bitmap = (result as BitmapDrawable).bitmap
                    val artworkData = bitmap.asBytes()
                    initializePlayer(artworkData)
                },
                onError = { 
                    initializePlayer(null)
                }
            )
            .build()
        imageLoader.enqueue(request)
    }

    private fun initializePlayer(artworkData: ByteArray?) {
        val audioUrl = intent.getStringExtra("AUDIO_URL") ?: return
        val episodeTitle = intent.getStringExtra("EPISODE_TITLE")

        player = ExoPlayer.Builder(this).build()
        mediaSession = MediaSession.Builder(this, player!!).build()

        binding.playerView.player = player

        val metadataBuilder = MediaMetadata.Builder()
            .setTitle(episodeTitle)
        if (artworkData != null) {
            metadataBuilder.setArtworkData(artworkData, MediaMetadata.PICTURE_TYPE_FRONT_COVER)
        }

        val mediaItem = MediaItem.Builder()
            .setUri(audioUrl)
            .setMediaMetadata(metadataBuilder.build())
            .build()

        player?.setMediaItem(mediaItem)
        player?.seekTo(playbackPosition)
        player?.prepare()
        player?.play()
    }

    override fun onStop() {
        super.onStop()
        // Save the current position before releasing the player
        player?.let { playbackPosition = it.currentPosition }
        releasePlayer()
    }

    private fun releasePlayer() {
        player?.release()
        player = null
        mediaSession?.release()
        mediaSession = null
    }

    private fun android.graphics.Bitmap.asBytes(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }
}
