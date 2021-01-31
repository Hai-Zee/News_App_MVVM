package com.example.newsappzeesh.network

import com.example.newsappzeesh.modal.Api
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

//    @GET("/v2/top-headlines")
//    suspend fun getBBCNewsApi(
//    @Query("sources") sources : String,
//    @Query("apiKey") apiKey : String
//    ) : Api


    //  Tech news
//    https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=730a60dec330429c8fc1a2d3eeec28fd
    @GET("/v2/top-headlines")
    suspend fun getTechNewsApi(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Api

    //    Entertainment news
//    https://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=730a60dec330429c8fc1a2d3eeec28fd
    @GET("/v2/top-headlines")
    suspend fun getEntertainmnetNewsApi(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Api


    //    Indian Top Headline News
//    https://newsapi.org/v2/top-headlines?country=in&apiKey=730a60dec330429c8fc1a2d3eeec28fd
    @GET("/v2/top-headlines")
    suspend fun getIndiaNewsApi(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
    ): Api


    //    BBC News
//    https://newsapi.org/v2/everything?domains=bbc.co.uk&language=en&apiKey=730a60dec330429c8fc1a2d3eeec28fd
    @GET("/v2/everything")
    suspend fun getBBCNewsApi(
        @Query("domains") domains: String,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String
    ): Api
}

