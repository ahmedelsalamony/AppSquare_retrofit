package com.example.ahmed.appsquare_retrofit.rest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.ahmed.appsquare_retrofit.MyApplication;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed on 5/10/2017.
 */

public class ApiClient {



    public static final String BASE_URL="https://api.github.com/";
    public static Retrofit retrofit=null;


    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control",
                            String.format("max-age=%d", 60))
                    .build();
        }
    };


    public static Retrofit getRetrofit()
    {

        File httpCacheDirectory = new File(MyApplication.getAppContext().getCacheDir(), "responses");
        int cacheSize = 10*1024*1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

//        client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);


        if (retrofit==null)
        {
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
}
