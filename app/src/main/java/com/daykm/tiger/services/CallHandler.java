package com.daykm.tiger.services;

import android.util.Log;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Establish default behavior for retrofit callbacks to be used across the app
 *
 * @param <T>
 */
public abstract class CallHandler<T> implements Callback<T> {

    public static final String TAG = CallHandler.class.getSimpleName();

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        throw new RuntimeException(t);
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.code() == 200) {
            response(response.body());
        } else {
            try {
                Log.i(TAG, response.errorBody().string());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    abstract public void response(T result);
}
