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
        //创建okHttpCildent
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(8000, TimeUnit.SECONDS)
                .writeTimeout(8000,TimeUnit.SECONDS)
                .build();
        //创建Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.CONST_WAN_ANDROID)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)
                .build();

        //创建代理类
        service = retrofit.create(HttpService.class);
    }

    //返回该单例类
    public static RetrofitManager getInstance(){
        if (manager == null){
            manager = new RetrofitManager();
        }
        return manager;
    }

    //获得代理类service
    public HttpService getService(){
        return service;
    }
}
