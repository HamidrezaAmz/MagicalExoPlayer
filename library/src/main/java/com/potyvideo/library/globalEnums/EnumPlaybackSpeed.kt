package com.potyvideo.library.globalEnums

enum class EnumPlaybackSpeed(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    PLUS_ONE("plus_one", 1),
    PLUS_FIVE("plus_five", 2),

    NORMAL("normal", 5),

    MINES_ONE("mines_one", 10),
    MINES_FIVE("mines_five", 11);

    companion object {

        operator fun get(value: String?): EnumPlaybackSpeed {
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

        operator fun get(value: Int?): EnumPlaybackSpeed {
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