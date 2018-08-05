package com.manwinder.nbcuniversalkotlin.ui

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_BUFFERING
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.manwinder.nbcuniversalkotlin.R
import kotlinx.android.synthetic.main.video_fragment.*


class VideoFragment : Fragment() {

    private lateinit var player : ExoPlayer

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loading_bar_video?.let {
            loading_bar_video.show()
        }

        savedInstanceState?.let {
            loadVideo(savedInstanceState.getLong("time"))
        } ?: run {
            loadVideo()
        }
    }

    override fun onPause() {
        player.release()
        super.onPause()
    }

    override fun onStop() {
        player.release()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong("time", player.currentPosition)
        super.onSaveInstanceState(outState)
    }


    private fun loadVideo(time : Long = 0) {
        arguments?.let {
            val url = it.getString("URL")

            // 1. Create a default TrackSelector
            val bandwidthMeter = DefaultBandwidthMeter()
            val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            val trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)

            // 2. Create the player
            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

            video.player = player

            // Produces DataSource instances through which media data is loaded.
            val userAgent = Util.getUserAgent(context, "User Agent")
            val dataSourceFactory = DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, 30000, true)

            val videoSource = HlsMediaSource.Factory(dataSourceFactory)
                    .setMinLoadableRetryCount(30000)
                    .setAllowChunklessPreparation(true)
                    .createMediaSource(Uri.parse(url))

            val listener = object: Player.DefaultEventListener() {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    super.onPlayerStateChanged(playWhenReady, playbackState)
                    when (playbackState) {
                        STATE_READY -> {
                            player.playWhenReady = true
                            loading_bar_video.hide()
                        }
                        STATE_BUFFERING -> loading_bar_video.show()
                    }
                }
            }
            player.addListener(listener)
            player.prepare(videoSource)
            player.seekTo(time)
        }
    }
}