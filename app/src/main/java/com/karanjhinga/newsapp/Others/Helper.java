package com.karanjhinga.newsapp.Others;

import android.content.Context;
import android.net.ConnectivityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

        // A HELPER METHOD TO CHECK IF WE ARE CONNECTED TO INTERNET
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
        // A HELPER METHOD TO TRANSFORM THE TIMESTAMP INTO BEAUTIFUL LOOKING DATE !
    public static String convertTimeStamp(String timestamp) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm a MMM dd, yyyy");
        Date date = inputFormat.parse(timestamp);
        return outputFormat.format(date);
    }


}
