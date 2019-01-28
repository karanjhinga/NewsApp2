package com.karanjhinga.newsapp.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

    /* IT IS THE TYPE OF RESPONSE THAT WE GET FROM SERVER WHEN WE CALL FOR SOURCES */

public class SourceList{

    @SerializedName("status")
    public String status;

    @SerializedName("sources")
    public List<Source> sources;
}
