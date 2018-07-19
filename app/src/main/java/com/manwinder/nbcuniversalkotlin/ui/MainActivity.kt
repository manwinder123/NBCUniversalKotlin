package com.manwinder.nbcuniversalkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.NewsFeedAdapter
import com.manwinder.nbcuniversalkotlin.model.NewsData
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.model.NewsItemViewModel
import com.manwinder.nbcuniversalkotlin.model.NewsResponse
import com.manwinder.nbcuniversalkotlin.network.NBCApi
import com.manwinder.nbcuniversalkotlin.util.Constants
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private val newsItemsList = ArrayList<NewsItem>()

    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news_feed.layoutManager = LinearLayoutManager(this)
        val newsFeedAdapter = NewsFeedAdapter(newsItemsList)
        news_feed.adapter = newsFeedAdapter

        val database = Room.databaseBuilder(this, com.manwinder.nbcuniversalkotlin.model.NewsItemDatabase::class.java, "news_items.db").build()

        val newsItemDao = database.newsItemDao()

        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel::class.java)
        newsItemViewModel.getNewsItems(newsItemDao)
        newsItemViewModel.getNewsItemsList().observe(this, Observer {
            it?.let {
                it -> newsItemsList.addAll(it)
                newsFeedAdapter.notifyItemInserted(newsItemsList.size)
            }
        })
    }
}
