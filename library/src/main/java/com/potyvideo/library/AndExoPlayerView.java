package com.potyvideo.library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.potyvideo.library.globalEnums.EnumResizeMode;
import com.potyvideo.library.utils.PublicValues;

public class AndExoPlayerView extends LinearLayout implements View.OnClickListener {

    private Context context;
    private String currSource = "";
    private boolean playWhenReady = false;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean isPreparing = false;
    private TypedArray typedArray = null;

    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView playerView;
    private ComponentListener componentListener;
    private LinearLayout linearLayoutRetry;
    private FrameLayout frameLayoutFullScreenContainer;
    private AppCompatImageButton imageViewEnterFullScreen, imageViewExitFullScreen;

    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;

    public class ComponentListener implements Player.EventListener {

        String TAG = AndExoPlayerView.ComponentListener.class.getSimpleName();

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case Player.STATE_READY:
                    if (isPreparing) {
                        // this is accurate
                        isPreparing = false;
                    }
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d(TAG, "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {
            linearLayoutRetry.setVisibility(VISIBLE);
        }

        @Override
        public void onPositionDiscontinuity(int reason) {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }

        @Override
        public void onSeekProcessed() {

        }
    }

    public AndExoPlayerView(Context context) {
        super(context);
        initializeView(context);
    }

    public AndExoPlayerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AndExoPlayerView,
                0, 0);
        initializeView(context);
    }

    public AndExoPlayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.AndExoPlayerView,
                0, 0);
        initializeView(context);
    }

    private void initializeView(Context context) {
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_player_base, this, true);

        playerView = view.findViewById(R.id.simpleExoPlayerView);
        linearLayoutRetry = findViewById(R.id.linearLayoutRetry);
        frameLayoutFullScreenContainer = playerView.findViewById(R.id.container_fullscreen);
        imageViewEnterFullScreen = playerView.findViewById(R.id.exo_enter_fullscreen);
        imageViewExitFullScreen = playerView.findViewById(R.id.exo_exit_fullscreen);

        componentListener = new ComponentListener();

        linearLayoutRetry.setOnClickListener(this);
        imageViewEnterFullScreen.setOnClickListener(this);
        imageViewExitFullScreen.setOnClickListener(this);

        if (typedArray != null) {

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_full_screen))
                setShowFullScreen(typedArray.getBoolean(R.styleable.AndExoPlayerView_andexo_full_screen, false));

            if (typedArray.hasValue(R.styleable.AndExoPlayerView_andexo_resize_mode)) {
                int resizeMode = typedArray.getInteger(R.styleable.AndExoPlayerView_andexo_resize_mode, EnumResizeMode.FILL.getValue());
                setResizeMode(EnumResizeMode.get(resizeMode));
            }

            typedArray.recycle();
        }

        int baseHeight = (int) getResources().getDimension(R.dimen.player_base_height);
        playerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, baseHeight));

        initializePlayer();
    }

    private void initializePlayer() {

        if (simpleExoPlayer == null) {

            bandwidthMeter = new DefaultBandwidthMeter();
            extractorsFactory = new DefaultExtractorsFactory();
            trackSelectionFactory = new AdaptiveTrackSelection.Factory();
            trackSelector = new DefaultTrackSelector();

            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

            playerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.setPlayWhenReady(playWhenReady);
            simpleExoPlayer.seekTo(currentWindow, playbackPosition);
        }
    }

    public void setSource(String source) {
        MediaSource mediaSource = buildMediaSource(source);
        if (mediaSource != null)
            simpleExoPlayer.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(String source) {

        if (source == null) {
            Toast.makeText(context, "Input Is Invalid.", Toast.LENGTH_SHORT).show();
            return null;
        }

        this.currSource = source;

        boolean validUrl = URLUtil.isValidUrl(source);

        Uri uri = Uri.parse(source);
        if (uri == null || uri.getLastPathSegment() == null) {
            Toast.makeText(context, "Uri Converter Failed, Input Is Invalid.", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (validUrl && uri.getLastPathSegment().contains(PublicValues.KEY_MP4)) {
            return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(PublicValues.KEY_USER_AGENT))
                    .createMediaSource(uri);
        } else if (!validUrl && uri.getLastPathSegment().contains(PublicValues.KEY_MP4)) {
            return new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(context, PublicValues.KEY_USER_AGENT))
                    .createMediaSource(uri);
        } else if (uri.getLastPathSegment().contains(PublicValues.KEY_HLS)) {
            return new HlsMediaSource.Factory(new DefaultHttpDataSourceFactory(PublicValues.KEY_USER_AGENT))
                    .createMediaSource(uri);
        } else {
            DefaultDashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(new DefaultHttpDataSourceFactory("ua", new DefaultBandwidthMeter()));
            DefaultHttpDataSourceFactory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(PublicValues.KEY_USER_AGENT);
            return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri);
        }
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.removeListener(componentListener);
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    public void setPlayWhenReady(boolean playWhenReady) {
        this.playWhenReady = playWhenReady;
    }

    public void stopPlayer() {
        if (simpleExoPlayer != null)
            simpleExoPlayer.stop();
    }

    public void hideController() {
        if (playerView != null)
            playerView.hideController();
    }

    public void setResizeMode(EnumResizeMode resizeMode) {
        switch (resizeMode) {

            case FIT:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                break;

            case FILL:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                break;

            case ZOOM:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                break;

            default:
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
        }
    }

    public void setShowFullScreen(boolean showFullScreen) {
        if (showFullScreen)
            frameLayoutFullScreenContainer.setVisibility(VISIBLE);
        else
            frameLayoutFullScreenContainer.setVisibility(GONE);
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releasePlayer();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checking the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //First Hide other objects (listview or recyclerview), better hide them using Gone.
            hideSystemUi();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) playerView.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            playerView.setLayoutParams(params);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //unhide your objects here.
            showSystemUi();
            int baseHeight = (int) getResources().getDimension(R.dimen.player_base_height);
            playerView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, baseHeight));
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        if (playerView == null)
            return;

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @SuppressLint("InlinedApi")
    private void showSystemUi() {
        if (playerView == null)
            return;

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    @Override
    public void onClick(View v) {

        int targetViewId = v.getId();
        if (targetViewId == R.id.linearLayoutRetry) {
            initializeView(context);
        } else if (targetViewId == R.id.exo_enter_fullscreen) {
            enterFullScreen();
        } else if (targetViewId == R.id.exo_exit_fullscreen) {
            exitFullScreen();
        }
    }

    private void enterFullScreen() {
        imageViewExitFullScreen.setVisibility(VISIBLE);
        imageViewEnterFullScreen.setVisibility(GONE);

        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void exitFullScreen() {
        imageViewExitFullScreen.setVisibility(GONE);
        imageViewEnterFullScreen.setVisibility(VISIBLE);

        if (getActivity() != null)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}
