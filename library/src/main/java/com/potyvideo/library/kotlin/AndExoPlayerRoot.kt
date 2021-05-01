package com.potyvideo.library.kotlin

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.potyvideo.library.R
import com.potyvideo.library.kotlin.globalEnums.*
import com.potyvideo.library.kotlin.utils.DoubleClick
import com.potyvideo.library.utils.PublicFunctions


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

    abstract var customClickListener: DoubleClick

    var currAspectRatio: EnumAspectRatio = EnumAspectRatio.ASPECT_16_9
    var currRepeatMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF
    var currPlayerSize: EnumPlayerSize = EnumPlayerSize.EXACTLY
    var currResizeMode: EnumResizeMode = EnumResizeMode.FILL
    var currMute: EnumMute = EnumMute.UNMUTE
    var currPlaybackSpeed: EnumPlaybackSpeed = EnumPlaybackSpeed.NORMAL

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

        // extract attrs
        extractAttrs(attributeSet)

        // listeners
        initListeners()

    }

    private fun extractAttrs(attributeSet: AttributeSet?) {

        attributeSet.let {
            val attributes: TypedArray = context.obtainStyledAttributes(it, R.styleable.AndExoPlayerView)
            if (attributes.hasValue(R.styleable.AndExoPlayerView_andexo_aspect_ratio)) {
                val aspectRatio = attributes.getInteger(R.styleable.AndExoPlayerView_andexo_aspect_ratio, EnumAspectRatio.ASPECT_16_9.value)
                setAspectRatio(EnumAspectRatio[aspectRatio])
            }

            attributes.recycle()
        }

        /*
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.BenefitView)
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.BenefitView_image))
        textView.text = attributes.getString(R.styleable.BenefitView_text)
        attributes.recycle()
        */
    }

    private fun initListeners() {
        retryButton.setOnClickListener(customClickListener)
        backwardView.setOnClickListener(customClickListener)
        forwardView.setOnClickListener(customClickListener)
        mute.setOnClickListener(customClickListener)
        unMute.setOnClickListener(customClickListener)
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

    protected fun setAspectRatio(aspectRatio: EnumAspectRatio) {
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

    protected fun setRepeatMode(repeatMode: EnumRepeatMode) {
        this.currRepeatMode = repeatMode
    }

    protected fun setResizeMode(resizeMode: EnumResizeMode) {
        when (resizeMode) {
            EnumResizeMode.FIT -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            EnumResizeMode.FILL -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            EnumResizeMode.ZOOM -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            else -> playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }

    protected fun setShowSetting(showSetting: Boolean = false) {
        if (showSetting)
            settingContainer.visibility = VISIBLE
        else
            settingContainer.visibility = GONE
    }

}
