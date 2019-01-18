package com.karanjhinga.newsapp.Others;

import com.karanjhinga.newsapp.Interfaces.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

    /* USING SINGLETON PATTERN */
public class ApiClient {

    private static final String base_url = "https://newsapi.org/v2/"; //OUR BASE URL
    public static final String api_key = "41ec70a4fab74996a51b7c3802822f44"; // MY API KEY
    private Retrofit retrofit;
    private static ApiClient mInstance;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if (mInstance == null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
