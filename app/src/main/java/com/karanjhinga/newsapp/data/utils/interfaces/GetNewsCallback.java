package com.karanjhinga.newsapp.data.utils.interfaces;

import com.karanjhinga.newsapp.data.models.Article;

import java.util.List;

public interface GetNewsCallback extends ErrorCallback{
    void onNewsFetched(List<Article> newsList);
}
