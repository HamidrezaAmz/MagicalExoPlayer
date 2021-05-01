package com.potyvideo.library.globalInterfaces;

public interface ExoPlayerCallBack {

    void onError();

    default void onSthHappened(){}
}
