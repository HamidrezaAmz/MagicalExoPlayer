package com.potyvideo.library.model

import androidx.databinding.BaseObservable
import com.potyvideo.library.globalEnums.EnumAspectRatio
import com.potyvideo.library.globalEnums.EnumRepeatMode
import com.potyvideo.library.globalEnums.EnumPlayerSize
import com.potyvideo.library.globalEnums.EnumResizeMode

class PlayerControllerModel : BaseObservable() {

    var mute: Boolean = false
    var enumAspectRatio: EnumAspectRatio = EnumAspectRatio.ASPECT_16_9
    var enumRepeatMode: EnumRepeatMode = EnumRepeatMode.REPEAT_OFF
    var enumPlayerSize: EnumPlayerSize = EnumPlayerSize.EXACTLY
    var enumResizeMode: EnumResizeMode = EnumResizeMode.FILL

}