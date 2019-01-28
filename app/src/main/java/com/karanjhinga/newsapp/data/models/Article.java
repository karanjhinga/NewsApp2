package com.karanjhinga.newsapp.data.models;

import com.google.gson.annotations.SerializedName;

public class Article {

    public int type;

    public Article(int type) {
        this.type = type;
    }

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("url")
    public String url;

    @SerializedName("urlToImage")
    public String urlToImage;

    @SerializedName("publishedAt")
    public String publishedAt;

    @SerializedName("content")
    public String content;
}
