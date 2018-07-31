package com.manwinder.nbcuniversalkotlin.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.manwinder.nbcuniversalkotlin.api.NBCApi
import com.manwinder.nbcuniversalkotlin.api.NBCRepository
import com.manwinder.nbcuniversalkotlin.database.DatabaseCreator
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.network.Resource

class NewsItemViewModel(application: Application) : AndroidViewModel(application) {

    var newsItems : LiveData<Resource<List<NewsItem>?>>?

    private var nbcRepository: NBCRepository? = null

    init {
        nbcRepository = NBCRepository(
            NBCApi.getNBCApi(),
            DatabaseCreator.database(application).newsItemDao()
        )

        newsItems = nbcRepository?.getNewsItems()
    }

    fun getLatestNewsItems(): LiveData<Resource<List<NewsItem>?>>? {
        return nbcRepository?.getNewsItems()
    }
}