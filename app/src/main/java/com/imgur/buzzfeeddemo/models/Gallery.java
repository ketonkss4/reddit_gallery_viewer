package com.imgur.buzzfeeddemo.models;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;
import com.imgur.buzzfeeddemo.network.MainService;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by Kevin Moturi on 6/3/2016.
 */
public class Gallery extends BaseObservable {

    @SerializedName("data")
    List<GalleryItem> items;

    public static Observable<Gallery> requestData(String subreddit) {
        return new MainService().getSubredditGallery(subreddit)
                .subscribeOn(Schedulers.io());
    }

    public List<GalleryItem> getItems() {
        return items;
    }
}
