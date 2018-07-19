package com.manwinder.nbcuniversalkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.NewsFeedAdapter
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.model.NewsItemViewModel
import com.manwinder.nbcuniversalkotlin.util.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val newsItemsList = ArrayList<NewsItem>()

    private lateinit var newsItemViewModel: NewsItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        news_feed.layoutManager = LinearLayoutManager(this)
        val newsFeedAdapter = NewsFeedAdapter(newsItemsList) {
            newsItem -> newsItemClick(newsItem)
        }
        news_feed.adapter = newsFeedAdapter
        news_feed.addItemDecoration(DividerItemDecoration(news_feed.context, DividerItemDecoration.VERTICAL))

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

//        news_feed.addOnItemTouchListener(RecyclerItemClickListener())
    }

    private fun newsItemClick(newsItem : NewsItem) {
        val args = Bundle()
        args.putString("URL", newsItem.url)
        val frag = ArticleFragment.newInstance()
        frag.arguments = args
        replaceFragment(R.id.main_container, frag)
    }

    override fun onBackPressed() {
        Log.d("BACVL", supportFragmentManager.backStackEntryCount.toString())
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
        } else {
            finish()
        }
    }
}
