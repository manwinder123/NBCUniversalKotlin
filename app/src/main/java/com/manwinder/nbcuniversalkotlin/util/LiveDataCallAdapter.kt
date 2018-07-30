package com.manwinder.nbcuniversalkotlin.util

import android.arch.lifecycle.LiveData
import com.manwinder.nbcuniversalkotlin.network.Resource
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * https://proandroiddev.com/building-modern-apps-using-the-android-architecture-guidelines-3238fff96f14
 *
 * https://github.com/AkshayChordiya/android-arch-news-sample/blob/e4cfa5aba8d131561dbbeb2958064d9bc0b02904/app/src/main/java/com/akshay/newsapp/utils/LiveDataCallAdapter.kt
 *
 * A Retrofit adapter that converts the Call into a LiveData of ApiResponse.
 * @param <R>
 */
class LiveDataCallAdapter<R>(private val responseType: Type) : CallAdapter<R, LiveData<Resource<R>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<R>): LiveData<Resource<R>> {
        return object : LiveData<Resource<R>>() {
            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(response.toResource())
                        }

                        override fun onFailure(call: Call<R>, throwable: Throwable) {
                            postValue(Resource.error(throwable.message))
                        }
                    })
                }
            }
        }
    }
}
