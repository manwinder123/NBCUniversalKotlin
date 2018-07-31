package com.manwinder.nbcuniversalkotlin.ui

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.NewsFeedAdapter
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.model.SlideShowImage
import com.manwinder.nbcuniversalkotlin.viewmodel.NewsItemViewModel
import com.manwinder.nbcuniversalkotlin.network.Resource
import com.manwinder.nbcuniversalkotlin.network.Status
import com.manwinder.nbcuniversalkotlin.util.getViewModel
import com.manwinder.nbcuniversalkotlin.util.replaceFragment
import com.manwinder.nbcuniversalkotlin.util.replaceFragmentFade
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val newsItemsList = ArrayList<NewsItem>()

    private val newsItemViewModel: NewsItemViewModel by lazy { getViewModel<NewsItemViewModel>()}

    private lateinit var snackbar : Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSnackbar()
        setupSwipeRefreshLayout()

        news_feed.layoutManager = LinearLayoutManager(this)
        val newsFeedAdapter = NewsFeedAdapter(newsItemsList) {
            newsItem -> newsItemClick(newsItem)
        }
        news_feed.adapter = newsFeedAdapter
        news_feed.addItemDecoration(DividerItemDecoration(news_feed.context, DividerItemDecoration.VERTICAL))

        swipe_refresh_layout.isRefreshing = true
        fab.hide()

        val newsItemObserver = Observer<Resource<List<NewsItem>?>> { resource->
            when(resource?.status) {
                Status.SUCCESS -> {
                    changeSnackbarText("News updated")
                    swipe_refresh_layout.isRefreshing = false
                    fab.show()

                    resource.data?.let { newsItemList ->
                        newsItemsList.addAll(newsItemList)
                        newsFeedAdapter.notifyItemInserted(newsItemList.size)
                    }
                }
                Status.ERROR-> {
                    changeSnackbarText("Issue with update")
                }
                Status.LOADING-> {}
            }
        }

        setupListeners(newsItemObserver)
    }

    private fun setupListeners(newsItemObserver: Observer<Resource<List<NewsItem>?>>) {

        newsItemViewModel.newsItems?.observe(this, newsItemObserver)

        swipe_refresh_layout.setOnRefreshListener {
            fab.hide()
            newsItemViewModel.getLatestNewsItems()?.observe(this, newsItemObserver)
        }

        fab.setOnClickListener {
            fab.hide()
            swipe_refresh_layout.isRefreshing = true
            newsItemViewModel.getLatestNewsItems()?.observe(this, newsItemObserver)
        }

        // Shows/hides the fab on scroll
        news_feed.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && fab.isShown) {
                    fab.hide()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
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

    private fun setupSwipeRefreshLayout() {
        swipe_refresh_layout.setColorSchemeColors(Color.WHITE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipe_refresh_layout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipe_refresh_layout.setProgressBackgroundColorSchemeColor(resources.getColor(R.color.colorPrimary))
        }
    }

    private fun setupSnackbar() {
        snackbar = Snackbar.make(main_container, "", Snackbar.LENGTH_SHORT)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun changeSnackbarText(text : String) {
        if (!snackbar.isShown) {
            snackbar.setText(text)
            snackbar.show()
        }
    }
}



