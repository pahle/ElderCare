package com.example.capstoneproject.ui.article

import com.example.capstoneproject.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {
    @GET("v2/everything")
    fun getNews(
        @Query("q") category: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}