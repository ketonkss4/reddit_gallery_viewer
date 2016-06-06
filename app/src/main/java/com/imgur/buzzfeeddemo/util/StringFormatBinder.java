package com.imgur.buzzfeeddemo.util;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import com.imgur.buzzfeeddemo.R;

/**
 * Created by Kevin Moturi on 6/4/2016.
 */
public class StringFormatBinder {

    @BindingAdapter("app:setViews")
    public static void setFormattedDate(TextView textView, String viewCount){
        Resources resources = textView.getContext().getResources();
        textView.setText(String.format(resources.getString(R.string.views), viewCount));
    }

}
