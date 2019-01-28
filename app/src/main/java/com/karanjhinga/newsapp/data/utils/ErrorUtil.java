package com.karanjhinga.newsapp.data.utils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;

public class ErrorUtil {

    public static String getError(Response response){
        try {
             JSONObject errorObject = new JSONObject(response.errorBody().string());
             return errorObject.getString("message");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getError(Throwable t){
        if (t instanceof UnknownHostException){
            return "No internet Connection";
        }else if(t instanceof SocketTimeoutException){
            return "Something went wrong, Try again later !";
        }else {
            return t.getMessage();
        }
    }
}
