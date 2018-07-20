package com.manwinder.nbcuniversalkotlin.network

import android.arch.lifecycle.LiveData
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.database.NewsItemDao
import com.manwinder.nbcuniversalkotlin.util.Constants
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.UnknownHostException
import javax.inject.Singleton

@Singleton
class NBCRepository() {

    private val nbcApi: NBCApi
    private lateinit var newsItemDao: NewsItemDao

    constructor(newsItemDao: NewsItemDao) : this() {
        this.newsItemDao = newsItemDao
    }

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.NBC_NEWS_DATA_SOURCE)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        nbcApi = retrofit.create(NBCApi::class.java)
    }

    fun getNewsItems(): LiveData<List<NewsItem>> {
        Completable.fromAction {

            try {
                val callResponse = nbcApi.getNewsItems().execute()
                if (callResponse.isSuccessful) {
                    callResponse.body().let {
                        newsResponse -> newsResponse?.data?.forEach {
                            newsItemList -> newsItemList.items?.forEach {
                                newsItem -> newsItem.type?. let {
                                    // curation type news items only contains a teaser image, nothing else
                                    if (!it.contains("curation") && newsItemDao.hasID(newsItem.id) == 0) {
                                        newsItemDao.insert(newsItem)
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: UnknownHostException) {
                // this exception is usually thrown when the device is not connected to the internet
            }
        }.subscribeOn(Schedulers.io()).subscribe()
        return newsItemDao.getNewsItemsInOrder()
    }
}