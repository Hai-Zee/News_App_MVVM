package com.example.newsappzeesh.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Article(

    @SerializedName("author")
    @Expose
    val author: Any,

    @SerializedName("content")
    @Expose
    val content: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("publishedAt")
    @Expose
    val time: String,

    @SerializedName("source")
    @Expose
    val source_name: Source,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("url")
    @Expose
    val newsUrl: String,

    @SerializedName("urlToImage")
    @Expose
    val imageUrl: String
)