package com.example.android.microinsurance.common;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkBuilder {

    private final Retrofit.Builder retrofitBuilder;

    private OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

    public NetworkBuilder(String baseUrl) {
        this.retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl);
    }

    public NetworkBuilder UseGson(){
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        return this;
    }

    public NetworkBuilder useRxJava2(){
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return this;
    }

    public <T> T build(Class<T> apiClass){
        return retrofitBuilder.client(okHttpClient).build().create(apiClass);
    }
}

