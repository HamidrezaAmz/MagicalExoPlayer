package com.potyvideo.library.globalEnums

enum class EnumAspectRatio(var valueStr: String, val value: Int) {

    UNDEFINE("UNDEFINE", 0),
    ASPECT_1_1("ASPECT_1_1", 1),
    ASPECT_16_9("ASPECT_16_9", 2),
    ASPECT_4_3("ASPECT_4_3", 3),
    ASPECT_MATCH("ASPECT_MATCH", 4),
    ASPECT_MP3("ASPECT_MP3", 5);

    companion object {
        
        operator fun get(value: String?): EnumAspectRatio {
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

        operator fun get(value: Int?): EnumAspectRatio {
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