package com.karanjhinga.newsapp.data.utils.interfaces;

import com.karanjhinga.newsapp.data.models.Source;

import java.util.List;

public interface GetSourcesCallback extends ErrorCallback{
    void onSourcesFetched(List<Source> sourceList);

}
