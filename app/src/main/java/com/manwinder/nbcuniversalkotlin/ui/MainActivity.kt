package com.manwinder.nbcuniversalkotlin.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.NewsFeedAdapter
import com.manwinder.nbcuniversalkotlin.database.MIGRATION_1_2
import com.manwinder.nbcuniversalkotlin.database.NewsItemDatabase
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.model.NewsItemViewModel
import com.manwinder.nbcuniversalkotlin.model.SlideShowImage
import com.manwinder.nbcuniversalkotlin.util.replaceFragment
import com.manwinder.nbcuniversalkotlin.util.replaceFragmentFade
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val newsItemsList = ArrayList<NewsItem>()

    private lateinit var newsItemViewModel: NewsItemViewModel

    private lateinit var snackbar : Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSnackbar()

        news_feed.layoutManager = LinearLayoutManager(this)
        val newsFeedAdapter = NewsFeedAdapter(newsItemsList) {
            newsItem -> newsItemClick(newsItem)
        }
        news_feed.adapter = newsFeedAdapter
        news_feed.addItemDecoration(DividerItemDecoration(news_feed.context, DividerItemDecoration.VERTICAL))

        val database = Room.databaseBuilder(this, NewsItemDatabase::class.java, "news_items.db")
                .addMigrations(MIGRATION_1_2)
                .build()

        val newsItemDao = database.newsItemDao()

        newsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel::class.java)
        newsItemViewModel.getNewsItems(newsItemDao)
        newsItemViewModel.newsItems.observe(this, Observer {
            it?.let {
                createSnackbar("News Updated")
                newsItemsList.addAll(it)
                newsFeedAdapter.notifyItemInserted(newsItemsList.size)
            }

        })

        fab.setOnClickListener {
            createSnackbar("Updating News")
            newsItemViewModel.getNewsItems(newsItemDao)
        }
    }

    private fun newsItemClick(newsItem : NewsItem) {
        val args = Bundle()
        fab.hide()
        when {
            newsItem.type == "video" -> {
                args.putString("URL", newsItem.videoUrl)
                val frag = VideoFragment.newInstance()
                frag.arguments = args
                replaceFragmentFade(R.id.main_container, frag)
            }
            newsItem.type == "slideshow" -> {
                newsItem.images?.let {
                    args.putParcelableArrayList("slideshow", it as ArrayList<SlideShowImage>)
                }
                val frag = SlideShowFragment.newInstance()

                frag.arguments = args
                replaceFragmentFade(R.id.main_container, frag)
            }
            else -> {
                args.putString("URL", newsItem.url)
                val frag = ArticleFragment.newInstance()
                frag.arguments = args
                replaceFragment(R.id.main_container, frag)
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount != 0) {
            supportFragmentManager.popBackStack()
            fab.show()
        } else {
            finish()
        }
    }

    private fun setupSnackbar() {
        snackbar = Snackbar.make(main_container, "", Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun createSnackbar(text : String) {
        if (!snackbar.isShown) {
            snackbar.setText(text)
            snackbar.show()
        }
    }
}



