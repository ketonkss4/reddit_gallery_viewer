package com.imgur.buzzfeeddemo.util;

/**
 * Created by Kevin Moturi on 6/4/2016.
 */
public interface RefreshCompleteListener {
    void onRefreshComplete();
    void onError(Throwable throwable);
}
