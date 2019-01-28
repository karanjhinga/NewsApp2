package com.karanjhinga.newsapp.data.source;

import com.karanjhinga.newsapp.data.models.NewsList;
import com.karanjhinga.newsapp.data.models.SourceList;
import com.karanjhinga.newsapp.data.source.remote.api.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRemoteRepository {

    public void getAllSources(){

        ApiClient.getInstance().getApi().getAllSources(ApiClient.API_KEY).enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {

            }

            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {

            }
        });
    }

    public void getAllSourcesByCategory(String category){

        ApiClient.getInstance().getApi().getSourcesByCategory(category,ApiClient.API_KEY).enqueue(new Callback<SourceList>() {
            @Override
            public void onResponse(Call<SourceList> call, Response<SourceList> response) {

            }

            @Override
            public void onFailure(Call<SourceList> call, Throwable t) {

            }
        });
    }

    public void getNews(String source,int page){

        ApiClient.getInstance().getApi().getNews(source,ApiClient.API_KEY,page,10).enqueue(new Callback<NewsList>() {
            @Override
            public void onResponse(Call<NewsList> call, Response<NewsList> response) {

            }

            @Override
            public void onFailure(Call<NewsList> call, Throwable t) {

            }
        });
    }
}
