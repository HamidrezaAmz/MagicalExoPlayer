package com.potyvideo.library.kotlin

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.util.MimeTypes
import com.potyvideo.library.kotlin.globalEnums.EnumRepeatMode
import com.potyvideo.library.kotlin.globalInterfaces.AndExoPlayerListener
import com.potyvideo.library.kotlin.utils.DoubleClick
import com.potyvideo.library.kotlin.utils.DoubleClickListener
import com.potyvideo.library.kotlin.utils.PublicFunctions
import com.potyvideo.library.kotlin.utils.PublicValues

class AndExoPlayerView(
        context: Context,
        attributeSet: AttributeSet) : AndExoPlayerRoot(context, attributeSet), Player.EventListener {

    private lateinit var currSource: String

    private var player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
    private var andExoPlayerListener: AndExoPlayerListener? = null
    private var playWhenReady: Boolean = false
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var currVolume: Float = 0f

    override var customClickListener: DoubleClick
        get() = DoubleClick(object : DoubleClickListener {
            override fun onSingleClickEvent(view: View) {
                when (view.id) {
                    retryButton.id -> {
                        hideRetryView()
                        restartPlayer()
                    }
                    mute.id -> {
                        unMutePlayer()
                    }
                    unMute.id -> {
                        mutePlayer()
                    }
                }
            }

            override fun onDoubleClickEvent(view: View) {
                when (view.id) {
                    backwardView.id -> {
                        seekBackward()
                    }
                    forwardView.id -> {
                        seekForward()
                    }
                }
            }
        })
        set(value) {}

    init {
        player.addListener(this)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        showRetryView(error.sourceException.message)
        andExoPlayerListener?.let {
            andExoPlayerListener!!.onExoPlayerError(errorMessage = error.sourceException.message)
        }
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {

            }
            ExoPlayer.STATE_ENDED -> {

            }
            ExoPlayer.STATE_IDLE -> {

            }
            ExoPlayer.STATE_READY -> {

            }
            else -> {

            }
        }
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity(reason: Int) {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onTimelineChanged(timeline: Timeline, reason: Int) {
    }

    private fun releasePlayer() {
        playWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.release()
    }

    private fun restartPlayer() {
        player.seekTo(0);
        player.prepare()
    }

    private fun buildMediaItem(source: String): MediaItem {

        return when (PublicFunctions.getMimeType(source)) {

            PublicValues.KEY_MP4 -> buildMediaItemMP4(source)
            PublicValues.KEY_M3U8 -> buildMediaHLS(source)
            PublicValues.KEY_MP3 -> buildMediaItemMP4(source)

            else -> buildMediaGlobal(source)
        }
    }

    private fun buildMediaItemMP4(source: String): MediaItem {
        return MediaItem.Builder()
                .setUri(source)
                .setMimeType(MimeTypes.APPLICATION_MP4)
                .build()
    }

    private fun buildMediaHLS(source: String): MediaItem {
        return MediaItem.Builder()
                .setUri(source)
                .setMimeType(MimeTypes.APPLICATION_M3U8)
                .build()
    }

    private fun buildMediaDash(source: String): MediaItem {
        return MediaItem.Builder()
                .setUri(source)
                .setMimeType(MimeTypes.APPLICATION_MPD)
                .build()
    }

    private fun buildMediaGlobal(source: String): MediaItem {
        return MediaItem.Builder()
                .setUri(source)
                .build()
    }

    fun setAndExoPlayerListener(andExoPlayerListener: AndExoPlayerListener) {
        this.andExoPlayerListener = andExoPlayerListener
    }

    fun setSource(source: String) {

        /*if (!PublicFunctions.isThisSourceSupported(source)) {
            showRetryView("This source is not supported!")
            return
        }*/

        currSource = source

        val mediaItem = buildMediaItem(source)

        playerView.player = player
        player.playWhenReady = true
        player.setMediaItem(mediaItem)
        player.prepare()

    }

    fun setShowTimeOut(showTimeoutMs: Int) {
        playerView.controllerShowTimeoutMs = showTimeoutMs
        if (showTimeoutMs == 0)
            playerView.controllerHideOnTouch = false
    }

    fun setResizeMode() {
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
    }

    fun seekBackward(backwardValue: Int = 10000) {
        var seekValue = player.currentPosition - backwardValue
        if (seekValue < 0) seekValue = 0
        player.seekTo(seekValue)
    }

    fun seekForward(ForwardValue: Int = 10000) {
        var seekValue = player.currentPosition + ForwardValue
        if (seekValue > player.duration) seekValue = player.duration
        player.seekTo(seekValue)
    }

    fun enableControllers(enableControllers: Boolean = true) {

    }

    fun mutePlayer() {
        currVolume = player.volume
        player.volume = 0f
        showMuteButton()
    }

    fun unMutePlayer() {
        player.volume = currVolume
        showUnMuteButton()
    }

    fun setLoopMode(repeatModeMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF) {
        when (repeatModeMode) {
            EnumRepeatMode.REPEAT_OFF -> {
                player.repeatMode = Player.REPEAT_MODE_OFF
            }
            EnumRepeatMode.REPEAT_ONE -> {
                player.repeatMode = Player.REPEAT_MODE_ONE
            }
            EnumRepeatMode.REPEAT_ALWAYS -> {
                player.repeatMode = Player.REPEAT_MODE_ALL
            }
        }
    }

}