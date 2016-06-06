package com.imgur.buzzfeeddemo.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Kevin Moturi on 6/4/2016.
 */
public class ImageBinder {
    @BindingAdapter("app:imgUrl")
    public static void setFormattedDate(ImageView view, String uri){
        if(TextUtils.isEmpty(uri)) return;
        Picasso.with(view.getContext()).load(uri).into(view);
    }
}
