package com.manwinder.nbcuniversalkotlin.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.model.NewsItem
import com.manwinder.nbcuniversalkotlin.util.inflate
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item_video_row.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsFeedAdapter(private val newsItems: ArrayList<NewsItem>, private val clickListener: (NewsItem) -> Unit) : RecyclerView.Adapter<NewsFeedAdapter.NewsHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (newsItems[position].type == "video") {
            1
        } else {
            0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val inflatedView : View = if (viewType == 0) {
            parent.inflate(R.layout.news_item_row, false)
        } else {
            parent.inflate(R.layout.news_item_video_row, false)
        }
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
            val headline= view.findViewById<TextView>(R.id.headline)
            val publishedDate = view.findViewById<TextView>(R.id.publish_date)
            val type = view.findViewById<TextView>(R.id.type)
            val tease= view.findViewById<ImageView>(R.id.tease)

            headline.text = newsItem.headline
            newsItem.published?.let {
                val date = dateFormatter.parse(it)
                if (DateUtils.isToday(date.time)) {
                    publishedDate.text = dateFormatToShowToday.format(date).toString()
                } else {
                    publishedDate.text = dateFormatToShow.format(date).toString()
                }
            }?: run {
                publishedDate.text = ""
            }
            type.text = newsItem.type
            newsItem.published

            if (URLUtil.isValidUrl(newsItem.tease)) {
                Picasso.get()
                        .load(newsItem.tease)
                        .centerCrop()
                        .fit()
                        .into(tease, object: Callback {
                            override fun onSuccess() {
                                if (newsItem.type == "video") {
                                    // changes play button color from black to white, makes it easier to see
                                    view.play.setColorFilter(Color.argb(255,255,255,255))
                                }
                            }

                            override fun onError(e: Exception?) {}
                        })

            }
            view.setOnClickListener {
                clickListener(newsItem)
            }
        }
    }
}