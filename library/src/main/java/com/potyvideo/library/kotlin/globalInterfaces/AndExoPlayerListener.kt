package com.potyvideo.library.kotlin.globalInterfaces

interface AndExoPlayerListener {

    fun onExoPlayerStart() {}

    fun onExoPlayerFinished() {}

    fun onExoPlayerLoading() {}

    fun onExoPlayerError(errorMessage: String?) {}

    fun onExoBuffering() {}

    fun onExoEnded() {}

    fun onExoIdle() {}

    fun onExoReady() {}

}