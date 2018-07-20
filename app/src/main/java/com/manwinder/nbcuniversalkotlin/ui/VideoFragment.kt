package com.manwinder.nbcuniversalkotlin.ui

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.kittinunf.fuel.Fuel
import com.manwinder.nbcuniversalkotlin.R
import kotlinx.android.synthetic.main.video_fragment.*
import java.io.File
import java.nio.charset.Charset


class VideoFragment : Fragment() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.video_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loading_bar_video?.let {
            loading_bar_video.show()
        }

        loadVideo()
    }

    private fun loadVideo() {
        arguments?.let {
            val url = it.getString("URL")

            video.setOnPreparedListener {
                video.start()
                loading_bar_video.hide()
            }

            Fuel.download(url).destination { _, _ ->
                File.createTempFile("temp", ".tmp")
            }.response { _, _, result ->
                val str = String(result.get(), Charset.defaultCharset())
                val arr = str.split("\n")
                video.setVideoURI(Uri.parse(arr[arr.size - 2]))
            }
        }
    }
}