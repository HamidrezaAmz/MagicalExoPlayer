package com.potyvideo.library.globalEnums

enum class EnumPlayerSize(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", -1),
    EXACTLY("EXACTLY", 0),
    AT_MOST("AT_MOST", 1),
    UNSPECIFIED("UNSPECIFIED", 2);

    companion object {

        operator fun get(value: String?): EnumPlayerSize {
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

        operator fun get(value: Int?): EnumPlayerSize {
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