package com.manwinder.nbcuniversalkotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manwinder.nbcuniversalkotlin.R
import com.manwinder.nbcuniversalkotlin.adapters.SlideShowAdapter
import com.manwinder.nbcuniversalkotlin.model.SlideShowImage
import kotlinx.android.synthetic.main.slide_show_fragment.*

class SlideShowFragment : Fragment() {

    private lateinit var slideShowList: ArrayList<SlideShowImage>

    companion object {
        fun newInstance() = SlideShowFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.slide_show_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        slide_show.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val slideShowAdapter = SlideShowAdapter(slideShowList)
        slide_show.adapter = slideShowAdapter
//        news_feed.addItemDecoration(DividerItemDecoration(news_feed.context, DividerItemDecoration.VERTICAL))

    }

    fun setSlideShowList(slideShowList: List<SlideShowImage>) {
        this.slideShowList = slideShowList as ArrayList<SlideShowImage>
    }
}