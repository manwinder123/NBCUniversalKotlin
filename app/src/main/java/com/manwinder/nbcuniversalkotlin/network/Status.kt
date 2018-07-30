package com.manwinder.nbcuniversalkotlin.network

/**
 * https://github.com/AkshayChordiya/android-arch-news-sample/blob/e4cfa5aba8d131561dbbeb2958064d9bc0b02904/app/src/main/java/com/akshay/newsapp/model/network/Status.kt
 *
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repository classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING;


    /**
     * Returns `true` if the [Status] is success else `false`.
     */
    fun isSuccessful() = this == SUCCESS

    /**
     * Returns `true` if the [Status] is loading else `false`.
     */
    fun isLoading() = this == LOADING
}