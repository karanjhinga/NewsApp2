package com.karanjhinga.newsapp.data.source;

import com.karanjhinga.newsapp.data.source.remote.NewsRemoteRepository;
import com.karanjhinga.newsapp.data.utils.interfaces.GetNewsCallback;
import com.karanjhinga.newsapp.data.utils.interfaces.GetSourcesCallback;

public class NewsRepository {
    private static final NewsRepository INSTANCE = new NewsRepository();
    private NewsRemoteRepository newsRemoteRepository;

    public static NewsRepository getInstance() {
        return INSTANCE;
    }

    private NewsRepository() {
        this.newsRemoteRepository = new NewsRemoteRepository();
    }

    public void getAllSources(GetSourcesCallback getSourcesCallback){
        newsRemoteRepository.getAllSources(getSourcesCallback);
    }

    public void getAllSourcesByCategory(String category,GetSourcesCallback getSourcesCallback){
        newsRemoteRepository.getAllSourcesByCategory(category,getSourcesCallback);
    }

    public void getNews(String source, int page, GetNewsCallback getNewsCallback){
        newsRemoteRepository.getNews(source,page,getNewsCallback);
    }
}
