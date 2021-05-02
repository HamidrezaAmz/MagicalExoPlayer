package com.potyvideo.library.globalEnums

enum class EnumRepeatMode(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    INFINITE("Infinite", 1),
    Finite("Finite", 2),
    REPEAT_OFF("REPEAT_OFF", 3),
    REPEAT_ONE("REPEAT_ONE", 4),
    REPEAT_ALWAYS("REPEAT_ALWAYS", 5);

    companion object {

        operator fun get(value: String?): EnumRepeatMode {
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

        operator fun get(value: Int?): EnumRepeatMode {
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