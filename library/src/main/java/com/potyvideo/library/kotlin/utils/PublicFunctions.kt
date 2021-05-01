package com.potyvideo.library.kotlin.utils

class PublicFunctions {

    companion object {

        fun isThisSourceSupported(source: String): Boolean {
            val mimeType = getMimeType(source)
            return PublicValues.SUPPORTED_MEDIAS.contains(mimeType)
        }

        fun getMimeType(source: String): String {
            return source.substringAfterLast(".", PublicValues.KEY_UNKNOWN).toLowerCase()
        }
    }
}