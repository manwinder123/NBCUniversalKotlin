package com.manwinder.nbcuniversalkotlin.api

import android.arch.lifecycle.LiveData
import com.manwinder.nbcuniversalkotlin.model.NewsResponse
import com.manwinder.nbcuniversalkotlin.network.Resource
import com.manwinder.nbcuniversalkotlin.util.LiveDataCallAdapterFactory
import okhttp3.HttpUrl
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface NBCApi {
    @GET("news.json")
    fun getNewsItems() : LiveData<Resource<NewsResponse>>

    companion object Factory {

        fun getNBCApi(NBC_NEWS_DATA_SOURCE: HttpUrl = HttpUrl.parse("https://s3.amazonaws.com/shrekendpoint/")!!): NBCApi {
            return Retrofit.Builder()
                    .baseUrl(NBC_NEWS_DATA_SOURCE)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .addCallAdapterFactory(LiveDataCallAdapterFactory())
                    .build()
                    .create(NBCApi::class.java)
        }
    }
}