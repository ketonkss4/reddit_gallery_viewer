package com.imgur.buzzfeeddemo.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kevin Moturi on 6/4/2016.
 */
public class ImageBinder {
    @BindingAdapter("app:imgUrl")
    public static void setFormattedDate(ImageView view, String uri){
        if(TextUtils.isEmpty(uri)) return;
        Picasso picassoSession = Picasso.with(view.getContext());
        picassoSession.setLoggingEnabled(true);
        picassoSession.load(uri.replace(".jpg", "t.jpg")).into(view);
    }
}
