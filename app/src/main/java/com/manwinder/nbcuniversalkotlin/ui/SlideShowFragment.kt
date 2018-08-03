package com.manwinder.nbcuniversalkotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
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

        arguments?.let {
            slideShowList = it.getParcelableArrayList("slideshow")
        }

        slide_show.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val slideShowAdapter = SlideShowAdapter(slideShowList)
        slide_show.adapter = slideShowAdapter

        val helper = PagerSnapHelper()
        helper.attachToRecyclerView(slide_show)
    }
}