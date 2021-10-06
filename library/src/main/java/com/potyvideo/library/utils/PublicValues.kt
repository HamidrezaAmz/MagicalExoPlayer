package com.potyvideo.library.utils

class PublicValues {

    companion object {

        val KEY_UNKNOWN = "unknown"
        val KEY_MP3 = "mp3"
        val KEY_MP3_CAPS = "MP3"
        val KEY_OGG = "ogg"
        val KEY_OGG_CAPS = "OGG"
        val KEY_MP4 = "mp4"
        val KEY_MP4_CAPS = "MP4"
        val KEY_M3U8 = "m3u8"
        val KEY_M3U8_CAPS = "M3U8"
        val KEY_MDP = "mdp"
        val KEY_MDP_CAPS = "MDP"
        val KEY_MKV = "mkv"
        val KEY_MKV_CAPS = "KKV"

        val SUPPORTED_MEDIAS = listOf(
                KEY_MP3,
                KEY_MP3_CAPS,
                KEY_OGG,
                KEY_OGG_CAPS,
                KEY_MP4,
                KEY_MP4_CAPS,
                KEY_M3U8,
                KEY_M3U8_CAPS,
                KEY_MDP,
                KEY_MDP_CAPS,
                KEY_MKV,
                KEY_MKV_CAPS
        )

        const val TEST_URL_MP4 = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4"
        const val TEST_URL_MP4_V2 = "https://hajifirouz2.cdn.asset.aparat.com/aparat-video/069670345914165d063b0738fc9f81b532132940-480p.mp4?wmsAuthSign=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbiI6ImMxMjg4ZDQzM2NhNzZhZmRjNGM1M2E3NTQyMjIwNTQwIiwiZXhwIjoxNjE4MzQ1NzA4LCJpc3MiOiJTYWJhIElkZWEgR1NJRyJ9.TbxCUzv7ke-TuYsxekYjN3VVnpG48uWOQ-7iN_ZH3_g"
        const val TEST_URL_MP4_V3 = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"

        const val TEST_URL_HLS = "https://content.jwplatform.com/manifests/yp34SRmf.m3u8"

        const val TEST_URL_DASH = "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd"

        const val request_code_select_video = 10001
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 10002

    }

}