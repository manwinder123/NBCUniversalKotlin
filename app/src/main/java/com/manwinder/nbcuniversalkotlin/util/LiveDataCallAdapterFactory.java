package com.manwinder.nbcuniversalkotlin.util;

import android.arch.lifecycle.LiveData;

import com.manwinder.nbcuniversalkotlin.network.Resource;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * https://github.com/AkshayChordiya/android-arch-news-sample/blob/e4cfa5aba8d131561dbbeb2958064d9bc0b02904/app/src/main/java/com/akshay/newsapp/utils/LiveDataCallAdapterFactory.java
 */

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(@NonNull Type returnType, @NonNull Annotation[] annotations, @NonNull Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType != Resource.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
