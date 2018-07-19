package com.manwinder.nbcuniversalkotlin.network

import com.manwinder.nbcuniversalkotlin.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NBCApi {
    @GET("/shrekendpoint/news.json")
    fun getNewsItems() : Call<NewsResponse>
}