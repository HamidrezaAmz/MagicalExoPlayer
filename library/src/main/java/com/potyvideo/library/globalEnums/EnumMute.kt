package com.potyvideo.library.globalEnums

enum class EnumMute(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    MUTE("mute", 1),
    UNMUTE("unmute", 2);

    companion object {

        operator fun get(value: String): EnumMute {
            if (value == null) {
                return UNDEFINE
            }
            val `arr$` = values()
            for (`val` in `arr$`) {
                if (`val`.valueStr.equals(value.trim { it <= ' ' }, ignoreCase = true)) {
                    return `val`
                }
            }
            return UNDEFINE
        }

        operator fun get(value: Int): EnumMute {
            if (value == null) {
                return UNDEFINE
            }
            val `arr$` = values()
            for (`val` in `arr$`) {
                if (`val`.value === value) {
                    return `val`
                }
            }
            return UNDEFINE
        }
    }
}