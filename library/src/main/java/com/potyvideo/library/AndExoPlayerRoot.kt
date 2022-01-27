package com.potyvideo.library

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.exoplayer2.ui.PlayerView
import com.potyvideo.library.globalEnums.*
import com.potyvideo.library.utils.DoubleClick

abstract class AndExoPlayerRoot @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var inflatedView: View = inflate(context, R.layout.layout_player_base_kotlin, this)

    var playerView: PlayerView
    var retryView: LinearLayout
    var retryViewTitle: TextView
    var retryButton: Button
    var backwardView: AppCompatButton
    var forwardView: AppCompatButton
    var mute: AppCompatImageButton
    var unMute: AppCompatImageButton
    var settingContainer: FrameLayout
    var fullScreenContainer: FrameLayout
    var enterFullScreen: AppCompatImageButton
    var exitFullScreen: AppCompatImageButton

    abstract var customClickListener: DoubleClick

    var currAspectRatio: EnumAspectRatio = EnumAspectRatio.ASPECT_16_9
    var currRepeatMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF
    var currPlayerSize: EnumPlayerSize = EnumPlayerSize.EXACTLY
    var currResizeMode: EnumResizeMode = EnumResizeMode.FILL
    var currMute: EnumMute = EnumMute.UNMUTE
    var currPlaybackSpeed: EnumPlaybackSpeed = EnumPlaybackSpeed.NORMAL
    var currScreenMode: EnumScreenMode = EnumScreenMode.MINIMISE

    init {

        // views
        playerView = inflatedView.findViewById(R.id.playerView)
        retryView = inflatedView.findViewById(R.id.retry_view)
        backwardView = inflatedView.findViewById(R.id.exo_backward)
        forwardView = inflatedView.findViewById(R.id.exo_forward)
        retryViewTitle = retryView.findViewById(R.id.textView_retry_title)
        retryButton = retryView.findViewById(R.id.button_try_again)
        mute = playerView.findViewById(R.id.exo_mute)
        unMute = playerView.findViewById(R.id.exo_unmute)
        settingContainer = playerView.findViewById(R.id.container_setting)
        fullScreenContainer = playerView.findViewById(R.id.container_fullscreen)
        enterFullScreen = playerView.findViewById(R.id.exo_enter_fullscreen)
        exitFullScreen = playerView.findViewById(R.id.exo_exit_fullscreen)

        // listeners
        initListeners()
    }

    private fun initListeners() {
        retryButton.setOnClickListener(customClickListener)
        backwardView.setOnClickListener(customClickListener)
        forwardView.setOnClickListener(customClickListener)
        mute.setOnClickListener(customClickListener)
        unMute.setOnClickListener(customClickListener)
        fullScreenContainer.setOnClickListener(customClickListener)
        enterFullScreen.setOnClickListener(customClickListener)
        exitFullScreen.setOnClickListener(customClickListener)
    }

    protected fun showRetryView() {
        showRetryView(null)
    }

    protected fun showRetryView(retryTitle: String?) {
        retryView.visibility = VISIBLE
        if (retryTitle != null)
            retryViewTitle.text = retryTitle
    }

    protected fun hideRetryView() {
        retryView.visibility = GONE
    }

    protected fun showLoading() {
        hideRetryView()
    }

    protected fun hideLoading() {
        hideRetryView()
    }

    protected fun setShowController(showController: Boolean = true) {
        if (showController)
            showController()
        else
            hideController()
    }

    protected fun showController() {
        playerView.showController()
    }

    protected fun hideController() {
        playerView.hideController()
    }

    protected fun showUnMuteButton() {
        mute.visibility = GONE
        unMute.visibility = VISIBLE
    }

    protected fun showMuteButton() {
        mute.visibility = VISIBLE
        unMute.visibility = GONE
    }

    protected fun setShowSettingButton(showSetting: Boolean = false) {
        if (showSetting)
            settingContainer.visibility = VISIBLE
        else
            settingContainer.visibility = GONE
    }

    protected fun setShowFullScreenButton(showFullscreenButton: Boolean = false) {
        if (showFullscreenButton)
            fullScreenContainer.visibility = VISIBLE
        else
            fullScreenContainer.visibility = GONE
    }

    protected fun setShowScreenModeButton(screenMode: EnumScreenMode = EnumScreenMode.MINIMISE) {
        when (screenMode) {
            EnumScreenMode.FULLSCREEN -> {
                enterFullScreen.visibility = GONE
                exitFullScreen.visibility = VISIBLE
            }
            EnumScreenMode.MINIMISE -> {
                enterFullScreen.visibility = VISIBLE
                exitFullScreen.visibility = GONE
            }
            else -> {
                enterFullScreen.visibility = VISIBLE
                exitFullScreen.visibility = GONE
            }
        }
    }

    protected fun showSystemUI() {
        playerView.systemUiVisibility = (SYSTEM_UI_FLAG_LOW_PROFILE
                or SYSTEM_UI_FLAG_IMMERSIVE
                or SYSTEM_UI_FLAG_FULLSCREEN
                or SYSTEM_UI_FLAG_LAYOUT_STABLE
                or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    protected fun hideSystemUI() {
        playerView.systemUiVisibility = (SYSTEM_UI_FLAG_LOW_PROFILE
                or SYSTEM_UI_FLAG_LAYOUT_STABLE)
    }

}
