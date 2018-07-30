package com.manwinder.nbcuniversalkotlin.api

import android.arch.lifecycle.LiveData
import com.manwinder.nbcuniversalkotlin.database.NewsItemDao
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.model.NewsResponse
import com.manwinder.nbcuniversalkotlin.network.NetworkBoundResource
import com.manwinder.nbcuniversalkotlin.network.Resource
import com.manwinder.nbcuniversalkotlin.util.AppExecutors
import javax.inject.Singleton

@Singleton
class NBCRepository(private var nbcApi: NBCApi, private var newsItemDao: NewsItemDao) {

    private val appExecutors = AppExecutors()

    fun getNewsItems(): LiveData<Resource<List<NewsItem>?>> {

        return object: NetworkBoundResource<List<NewsItem>, NewsResponse>(appExecutors) {
            override fun saveCallResult(item: NewsResponse) {
                item.data.forEach { newsData ->
                    newsData.items?.forEach { newsItem ->
                        newsItem.type?.let { type ->
                            // curation type news items only contains a teaser image, nothing else
                            if (!type.contains("curation") && newsItemDao.hasID(newsItem.id) == 0) {
                                newsItemDao.insert(newsItem)
                            }

                        }
                    }
                }
            }

            override fun shouldFetch(data: List<NewsItem>?): Boolean = true

            override fun loadFromDb(): LiveData<List<NewsItem>> = newsItemDao.getNewsItemsInOrder()

            override fun createCall(): LiveData<Resource<NewsResponse>> = nbcApi.getNewsItems()

        }.asLiveData()
    }
}