package com.potyvideo.andexoplayer;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.potyvideo.library.AndExoPlayerView;

public class MainActivity extends AppCompatActivity {

    private AndExoPlayerView andExoPlayerView;

    private String TEST_URL_MP4 = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_5mb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        andExoPlayerView.setSource(Uri.parse(TEST_URL_MP4));

        // andExoPlayerView.setShowFullScreen(true);
        // andExoPlayerView.setResizeMode(EnumResizeMode.FILL);
    }
}
