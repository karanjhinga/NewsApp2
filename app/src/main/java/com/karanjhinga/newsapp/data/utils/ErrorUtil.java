package com.karanjhinga.newsapp.data.utils;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
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
}
