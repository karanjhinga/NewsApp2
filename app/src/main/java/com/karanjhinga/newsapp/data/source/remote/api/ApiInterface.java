package com.karanjhinga.newsapp.data.source.remote.api;

import com.karanjhinga.newsapp.data.models.NewsList;
import com.karanjhinga.newsapp.data.models.SourceList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    // FUNCTION TO FETCH ALL SOURCES
    @GET(Routes.SOURCES)
    Call<SourceList> getAllSources(@Query("apiKey") String apiKey);

    // FUNCTION TO FETCH SOURCES SPECIFIC TO A CATEGORY
    @GET(Routes.SOURCES)
    Call<SourceList> getSourcesByCategory(@Query("category")String category,@Query("apiKey") String apiKey);

    // FUNCTION TO FETCH NEWS PAGE BY PAGE
    @GET(Routes.EVERYTHING)
    Call<NewsList> getNews(@Query("sources") String source, @Query("apiKey") String apiKey,@Query("page")int page,@Query("pageSize") int pageSize);

}
