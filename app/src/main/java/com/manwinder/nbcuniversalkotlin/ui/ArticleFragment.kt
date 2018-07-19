package com.manwinder.nbcuniversalkotlin.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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


    /**
     * Could use getSupportFragmentManager().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
     * in MainActivity.kt to do the animation but then its sluggish (cause the article starts loading).
     *
     * Using this way allows the animation to finish before loading the article content
     *
     */
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        var animation : Animation
        Log.d("enter", "enter: $enter")
        if (enter) {
            animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left)
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationEnd(p0: Animation?) {
                    loadArticle()
                }

                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationRepeat(p0: Animation?) {}
            })
        } else {
            animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right)
        }
        return animation
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loading_bar?.let {
            loading_bar.show()
        }
    }

    private fun loadArticle() {
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

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loading_bar?.let {
                    loading_bar.hide()
                }
            }
        }
        arguments?.let {
            val url = it.getString("URL")
            web_view.loadUrl(url)
        }
    }
}