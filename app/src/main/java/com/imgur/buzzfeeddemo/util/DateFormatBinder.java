package com.imgur.buzzfeeddemo.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Kevin Moturi on 6/4/2016.
 */
public class DateFormatBinder {

    @BindingAdapter("app:epochTimeStamp")
    public static void setFormattedDate(TextView textView, String epochTime){
        if(TextUtils.isEmpty(epochTime)) return;
        JodaTimeAndroid.init(textView.getContext());
        DateTime dateTime = new DateTime( Integer.valueOf(epochTime)*1000L);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("EEE, MMM yyyy h:mm a");
        dateTimeFormatter.withLocale(Locale.US);
        String date = dateTimeFormatter.print(dateTime);
        textView.setText(date);
    }
}
