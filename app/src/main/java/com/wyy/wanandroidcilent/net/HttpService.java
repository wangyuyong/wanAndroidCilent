package com.wyy.wanandroidcilent.net;

import com.wyy.wanandroidcilent.enity.Article;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.enity.Project;
import com.wyy.wanandroidcilent.enity.Validation;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

//Retrofit发送请求的接口
public interface HttpService {

    @GET("article/list/{page}/json")
    Observable<Article> getArticleData(@Path("page")int page);

    @GET("banner/json")
    Observable<BannerData> getBannerData();

    @GET("project/list/{page}/json?cid=294")
    Observable<Project> getProject(@Path("page")int page);

    @POST("user/login")
    @FormUrlEncoded
    Observable<Validation> getValidation(@Field("username")String userName,@Field("password")String password);

    @POST("user/register")
    @FormUrlEncoded
    Observable<Validation> getValidation(@Field("username")String userName,@Field("password")String password,@Field("repassword")String repassword);

    @POST("article/query/{page}/json")
    @FormUrlEncoded
    Observable<Article> getReacher(@Path("page")int page,@Field("k")String searchContent);
}
