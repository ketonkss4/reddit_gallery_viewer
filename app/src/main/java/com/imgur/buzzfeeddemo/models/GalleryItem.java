package com.imgur.buzzfeeddemo.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.google.gson.annotations.SerializedName;
import com.imgur.buzzfeeddemo.BR;

/**
 * Created by Kevin Moturi on 6/3/2016.
 */
public class GalleryItem extends BaseObservable {

    String id;
    String type;
    String datetime;
    String title;
    String description;
    @SerializedName("link")
    String img;
    String score;
    String views;

    public interface GalleryItemClickListener{
        View.OnClickListener onItemClick(GalleryItem item);
    }


    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public String getType() {
        return type;
    }

    @Bindable
    public String getDatetime() {
        return datetime;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getImg() {
        return img;
    }

    @Bindable
    public String getScore() {
        return score;
    }

    @Bindable
    public String getViews() {
        return views;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
        notifyPropertyChanged(BR.datetime);
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    public void setImg(String img) {
        this.img = img;
        notifyPropertyChanged(BR.img);
    }

    public void setScore(String score) {
        this.score = score;
        notifyPropertyChanged(BR.score);
    }

    public void setViews(String views) {
        notifyPropertyChanged(BR.views);
    }
}
