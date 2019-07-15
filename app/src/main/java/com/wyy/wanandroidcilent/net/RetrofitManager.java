package com.wyy.wanandroidcilent.net;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wyy.wanandroidcilent.Const;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//创建网络返回的单例类
public class RetrofitManager {

    private static RetrofitManager manager;
    private HttpService service;

    private RetrofitManager(){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.CONST_WAN_ANDROID)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)
                .build();

        service = retrofit.create(HttpService.class);
    }

    public static RetrofitManager getInstance(){
        if (manager == null){
            manager = new RetrofitManager();
        }
        return manager;
    }

    public HttpService getService(){
        return service;
    }
}
