package com.manwinder.nbcuniversalkotlin.util

import android.os.Bundle
import android.support.annotation.LayoutRes

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manwinder.nbcuniversalkotlin.model.SlideShowImage

// https://www.raywenderlich.com/170075/android-recyclerview-tutorial-kotlin
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


//https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func()
            .addToBackStack(null)
            .commit()
}

fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}

inline fun FragmentManager.inTransactionFade(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .addToBackStack(null)
            .commit()
}

fun AppCompatActivity.replaceFragmentFade(frameId: Int, fragment: Fragment) {
    supportFragmentManager.inTransactionFade{replace(frameId, fragment)}
}
