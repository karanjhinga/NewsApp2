package com.karanjhinga.newsapp.Data.Source.remote;

import com.karanjhinga.newsapp.Data.Models.NewsList;
import com.karanjhinga.newsapp.Data.Models.SourceList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // FUNCTION TO FETCH ALL SOURCES
    @GET("sources")
    Call<SourceList> getAllSources(@Query("apiKey") String apiKey);

    // FUNCTION TO FETCH SOURCES SPECIFIC TO A CATEGORY
    @GET("sources")
    Call<SourceList> getSourcesByCategory(@Query("category")String category,@Query("apiKey") String apiKey);

    // FUNCTION TO FETCH NEWS PAGE BY PAGE
    @GET("everything")
    Call<NewsList> getNews(@Query("sources") String source, @Query("apiKey") String apiKey,@Query("page")int page,@Query("pageSize") int pageSize);

}
