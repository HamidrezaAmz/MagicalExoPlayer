package com.potyvideo.andexoplayer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.potyvideo.library.AndExoPlayerView;
import com.potyvideo.library.utils.PathUtil;
import com.potyvideo.library.utils.PublicFunctions;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private AndExoPlayerView andExoPlayerView;

    private String TEST_URL_MP4 = "https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_5mb.mp4";

    private String TEST_URL_HLS = "http://185.23.131.61/hls/jomhori_2.m3u8";

    private int req_code = 129;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        andExoPlayerView = findViewById(R.id.andExoPlayerView);

        // andExoPlayerView.setShowFullScreen(true);
        // andExoPlayerView.setResizeMode(EnumResizeMode.FILL);

        findViewById(R.id.local).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocaleVideo();
            }
        });

        findViewById(R.id.mp4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMP4ServerSide();
            }
        });

        findViewById(R.id.hls).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHls();
            }
        });

    }

    private void loadHls() {
        andExoPlayerView.setSource(TEST_URL_HLS);
    }

    private void loadMP4ServerSide() {
        andExoPlayerView.setSource(TEST_URL_MP4);
    }

    private void selectLocaleVideo() {
        if (PublicFunctions.checkAccessStoragePermission(this)) {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Video"), req_code);
        }
    }

    private void loadMP4Locale(String filePath) {
        andExoPlayerView.setSource(filePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == req_code && resultCode == RESULT_OK) {
            Uri finalVideoUri = data.getData();
            String filePath = null;
            try {
                filePath = PathUtil.getPath(this, finalVideoUri);
                loadMP4Locale(filePath);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
