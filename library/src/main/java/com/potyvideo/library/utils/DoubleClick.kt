package com.potyvideo.library.utils

import android.os.Handler
import android.view.View

open class DoubleClick(private val doubleClickListener: DoubleClickListener, private var interval: Long = 200L) : View.OnClickListener {

    private val handler = Handler()
    private var counterClicks = 0
    private var isBusy = false

    override fun onClick(view: View) {
        if (!isBusy) {
            isBusy = true

            counterClicks++
            handler.postDelayed({
                if (counterClicks >= 2) {
                    doubleClickListener.onDoubleClickEvent(view)
                }
                if (counterClicks == 1) {
                    doubleClickListener.onSingleClickEvent(view)
                }

                counterClicks = 0
            }, interval)
            isBusy = false
        }
    }

}