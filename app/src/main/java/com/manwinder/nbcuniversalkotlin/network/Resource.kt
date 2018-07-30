package com.manwinder.nbcuniversalkotlin.network

/**
 * https://github.com/AkshayChordiya/android-arch-news-sample/blob/e4cfa5aba8d131561dbbeb2958064d9bc0b02904/app/src/main/java/com/akshay/newsapp/model/network/Resource.kt
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<ResultType>(var status: Status, var data: ResultType? = null, var errorMessage: String? = null) {

    companion object {
        /**
         * Creates [Resource] object with `SUCCESS` status and [data].
         */
        fun <ResultType> success(data: ResultType): Resource<ResultType> = Resource(Status.SUCCESS, data)

        /**
         * Creates [Resource] object with `LOADING` status to notify
         * the UI to showing loading.
         */
        fun <ResultType> loading(): Resource<ResultType> = Resource(Status.LOADING)

        /**
         * Creates [Resource] object with `ERROR` status and [message].
         */
        fun <ResultType> error(message: String?): Resource<ResultType> = Resource(Status.ERROR, errorMessage = message)
    }
}