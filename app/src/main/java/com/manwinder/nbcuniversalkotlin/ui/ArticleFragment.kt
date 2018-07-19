package com.manwinder.nbcuniversalkotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.manwinder.nbcuniversalkotlin.R
import kotlinx.android.synthetic.main.article_fragment.*

class ArticleFragment : Fragment() {
    companion object {
        fun newInstance() = ArticleFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.article_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                view?.let {
                    val view = it
                    url?.let {
                        view.loadUrl(it)
                    }
                }
                return true
            }
        }
        web_view.settings.javaScriptEnabled = true
        arguments?.let {
            val url = it.getString("URL")
            web_view.loadUrl(url)
        }
    }
}