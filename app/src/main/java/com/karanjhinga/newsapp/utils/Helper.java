package com.karanjhinga.newsapp.utils;

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



}
