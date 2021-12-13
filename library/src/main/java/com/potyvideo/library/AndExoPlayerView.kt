package com.potyvideo.library

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.util.MimeTypes
import com.potyvideo.library.globalEnums.EnumAspectRatio
import com.potyvideo.library.globalEnums.EnumMute
import com.potyvideo.library.globalEnums.EnumRepeatMode
import com.potyvideo.library.globalEnums.EnumResizeMode
import com.potyvideo.library.globalInterfaces.AndExoPlayerListener
import com.potyvideo.library.utils.DoubleClick
import com.potyvideo.library.utils.DoubleClickListener
import com.potyvideo.library.utils.PublicFunctions
import com.potyvideo.library.utils.PublicValues

class AndExoPlayerView(
        context: Context,
        attributeSet: AttributeSet) : AndExoPlayerRoot(context, attributeSet), Player.EventListener {

    private lateinit var currSource: String

    private var player: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
    private var andExoPlayerListener: AndExoPlayerListener? = null
    private var currPlayWhenReady: Boolean = false
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var currVolume: Float = 0f
    private var showControllers: Boolean = true

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
        extractAttrs(attributeSet)
    }

    private fun extractAttrs(attributeSet: AttributeSet?) {

        attributeSet.let {

            val typedArray: TypedArray = context.obtainStyledAttributes(it, R.styleable.AndExoPlayerView)

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_aspect_ratio)) {
                val aspectRatio = typedArray.getInteger(R.styleable.AndExoPlayerView_andexo_aspect_ratio, EnumAspectRatio.ASPECT_16_9.value)
                setAspectRatio(EnumAspectRatio[aspectRatio])
            }

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_resize_mode)) {
                val resizeMode: Int = typedArray.getInteger(R.styleable.AndExoPlayerView_andexo_resize_mode, EnumResizeMode.FILL.value)
                setResizeMode(EnumResizeMode[resizeMode])
            }

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_play_when_ready)) {
                setPlayWhenReady(typedArray.getBoolean(R.styleable.AndExoPlayerView_andexo_play_when_ready, false))
            }

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_mute)) {
                val muteValue = typedArray.getInteger(R.styleable.AndExoPlayerView_andexo_mute, EnumMute.UNMUTE.value)
                setMute(EnumMute[muteValue])
            }

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_show_controller)) {

            }

            typedArray.recycle()
        }
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {
    }

    override fun onPlayerError(error: ExoPlaybackException) {
        showRetryView(error.sourceException.message)
        andExoPlayerListener?.onExoPlayerError(error.sourceException.message)
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> {
                andExoPlayerListener?.onExoBuffering()
            }
            ExoPlayer.STATE_ENDED -> {
                andExoPlayerListener?.onExoEnded()
            }
            ExoPlayer.STATE_IDLE -> {
                andExoPlayerListener?.onExoIdle()
            }
            ExoPlayer.STATE_READY -> {
                andExoPlayerListener?.onExoReady()
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

    fun releasePlayer() {
        currPlayWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.stop()
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

        currSource = source

        val mediaItem = buildMediaItem(source)

        playerView.player = player
        player.playWhenReady = true
        player.setMediaItem(mediaItem)
        player.prepare()
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

    fun setShowControllers(showControllers: Boolean = true) {
        if (showControllers)
            setShowTimeOut(4000)
        else
            setShowTimeOut(0)
    }

    fun setShowTimeOut(showTimeoutMs: Int) {
        playerView.controllerShowTimeoutMs = showTimeoutMs
        if (showTimeoutMs == 0)
            playerView.controllerHideOnTouch = false
    }

    fun setMute(mute: EnumMute) {
        when (mute) {
            EnumMute.MUTE -> {
                mutePlayer()
            }
            EnumMute.UNMUTE -> {
                unMutePlayer()
            }
            else -> {
                unMutePlayer()
            }
        }
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

    fun setRepeatMode(repeatMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF) {
        this.currRepeatMode = repeatMode
        when (repeatMode) {
            EnumRepeatMode.REPEAT_OFF -> {
                player.repeatMode = Player.REPEAT_MODE_OFF
            }
            EnumRepeatMode.REPEAT_ONE -> {
                player.repeatMode = Player.REPEAT_MODE_ONE
            }
            EnumRepeatMode.REPEAT_ALWAYS -> {
                player.repeatMode = Player.REPEAT_MODE_ALL
            }
            else -> {
                player.repeatMode = Player.REPEAT_MODE_OFF
            }
        }
    }

    fun setAspectRatio(aspectRatio: EnumAspectRatio) {
        this.currAspectRatio = aspectRatio
        val value = PublicFunctions.getScreenWidth()
        when (aspectRatio) {
            EnumAspectRatio.ASPECT_1_1 -> playerView.layoutParams = FrameLayout.LayoutParams(value, value)
            EnumAspectRatio.ASPECT_4_3 -> playerView.layoutParams = FrameLayout.LayoutParams(value, 3 * value / 4)
            EnumAspectRatio.ASPECT_16_9 -> playerView.layoutParams = FrameLayout.LayoutParams(value, 9 * value / 16)
            EnumAspectRatio.ASPECT_MATCH -> playerView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            EnumAspectRatio.ASPECT_MP3 -> {
                playerView.controllerShowTimeoutMs = 0
                playerView.controllerHideOnTouch = false
                val mp3Height = context.resources.getDimensionPixelSize(R.dimen.player_controller_base_height)
                playerView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mp3Height)
            }
            EnumAspectRatio.UNDEFINE -> {
                val baseHeight = resources.getDimension(R.dimen.player_base_height).toInt()
                playerView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, baseHeight)
            }
        }
    }

    fun setResizeMode(resizeMode: EnumResizeMode) {
        when (resizeMode) {
            EnumResizeMode.FIT -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            EnumResizeMode.FILL -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            EnumResizeMode.ZOOM -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            else -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }

    fun setPlayWhenReady(playWhenReady: Boolean = false) {
        this.currPlayWhenReady = playWhenReady
        player.playWhenReady = playWhenReady
    }

    fun pausePlayer() {
        player.playWhenReady = false
        player.playbackState
    }

    fun stopPlayer() {
        player.stop()
        player.playbackState
    }

    fun startPlayer() {
        player.playWhenReady = true
        player.playbackState
    }

}