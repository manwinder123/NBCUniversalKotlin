package com.manwinder.nbcuniversalkotlin.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.util.inflate
import kotlinx.android.synthetic.main.news_item_row.view.*

class NewsFeedAdapter(private val newsItems: ArrayList<NewsItem>) : RecyclerView.Adapter<NewsFeedAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflatedView = parent.inflate(R.layout.news_item_row, false)
        return NewsHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = newsItems[position]
        holder.bindNewsItem(newsItem)
    }


    class NewsHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            Log.d("RecyclerView", "CLICK")
        }

        fun bindNewsItem(newsItem: NewsItem) {
            view.headline.text = newsItem.headline
            view.publish_date.text = newsItem.published
        }
    }
}