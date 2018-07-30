package com.manwinder.nbcuniversalkotlin.adapters

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.util.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsFeedAdapter(private val newsItems: ArrayList<NewsItem>, private val clickListener: (NewsItem) -> Unit) : RecyclerView.Adapter<NewsFeedAdapter.NewsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflatedView = parent.inflate(R.layout.news_item_row, false)
        return NewsHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val newsItem = newsItems[position]
        holder.bindNewsItem(newsItem, clickListener)
    }

    class NewsHolder(v: View): RecyclerView.ViewHolder(v) {
        private var view: View = v
        private val dateFormatToShow = SimpleDateFormat("EEE, MMM d, ''yy", Locale.getDefault())
        private val dateFormatToShowToday = SimpleDateFormat("h:mm a", Locale.getDefault())
        private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())


        fun bindNewsItem(newsItem: NewsItem, clickListener: (NewsItem) -> Unit) {
            view.headline.text = newsItem.headline
            newsItem.published?.let {
                val date = dateFormatter.parse(it)
                if (DateUtils.isToday(date.time)) {
                    view.publish_date.text = dateFormatToShowToday.format(date).toString()
                } else {
                    view.publish_date.text = dateFormatToShow.format(date).toString()
                }
            }?: run {
                view.publish_date.text = ""
            }
            view.type.text = newsItem.type
            newsItem.published

            if (URLUtil.isValidUrl(newsItem.tease)) {
                Picasso.get()
                        .load(newsItem.tease)
                        .centerCrop()
                        .fit()
                        .into(view.tease)
            }
            view.setOnClickListener {
                clickListener(newsItem)
            }
        }
    }
}