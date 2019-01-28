package com.karanjhinga.newsapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

        // A HELPER METHOD TO TRANSFORM THE TIMESTAMP INTO BEAUTIFUL LOOKING DATE !

    public static String convertTimeStamp(String timestamp) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm a MMM dd, yyyy");
        Date date = inputFormat.parse(timestamp);
        return outputFormat.format(date);
    }
}
