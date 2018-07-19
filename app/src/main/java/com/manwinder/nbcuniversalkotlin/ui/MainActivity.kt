package com.manwinder.nbcuniversalkotlin.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.NewsFeedAdapter
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newsFeedList = ArrayList<NewsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news_feed.layoutManager = LinearLayoutManager(this)
        val newsFeedAdapter = NewsFeedAdapter(newsFeedList)
        news_feed.adapter = newsFeedAdapter
    }
}
