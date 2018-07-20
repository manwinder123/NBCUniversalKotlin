package com.manwinder.nbcuniversalkotlin.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.manwinder.nbcuniversalkotlin.database.NewsItemDao
import com.manwinder.nbcuniversalkotlin.network.NBCRepository

class NewsItemViewModel : ViewModel() {

    lateinit var newsItems : LiveData<List<NewsItem>>

    fun getNewsItems(newsItemDao : NewsItemDao){
        val nbcRepository = NBCRepository(newsItemDao)
        newsItems = nbcRepository.getNewsItems()
    }
}