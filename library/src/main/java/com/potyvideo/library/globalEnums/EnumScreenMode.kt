package com.potyvideo.library.globalEnums

enum class EnumScreenMode(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    FULLSCREEN("fullscreen", 1),
    MINIMISE("minimise", 2);

    companion object {

        operator fun get(value: String): EnumScreenMode {
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

        operator fun get(value: Int): EnumScreenMode {
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