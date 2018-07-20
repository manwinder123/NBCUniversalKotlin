package com.manwinder.nbcuniversalkotlin.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.model.SlideShowImage
import com.manwinder.nbcuniversalkotlin.util.inflate
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.slide_show_item.view.*
import java.lang.Exception

class SlideShowAdapter(private val slideShowList: ArrayList<SlideShowImage>) : RecyclerView.Adapter<SlideShowAdapter.SlideShowHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideShowHolder {
        val inflatedView = parent.inflate(R.layout.slide_show_item, false)
        return SlideShowHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return slideShowList.size
    }

    override fun onBindViewHolder(holder: SlideShowHolder, position: Int) {
        holder.bind(slideShowList[position])
    }


    class SlideShowHolder(v: View): RecyclerView.ViewHolder(v) {
        private val view: View = v

        fun bind(slideShowImage: SlideShowImage) {
            view.caption.text = slideShowImage.caption
            view.copyright.text = slideShowImage.copyright
            view.loading_bar_slide_show.show()
            if (URLUtil.isValidUrl(slideShowImage.url)) {
                Picasso.get()
                        .load(slideShowImage.url)
                        .into(view.slide_show_image, object: Callback {
                            override fun onSuccess() {
                                view.loading_bar_slide_show.hide()
                            }
                            override fun onError(e: Exception?) {}
                        })
            }
        }

    }
}