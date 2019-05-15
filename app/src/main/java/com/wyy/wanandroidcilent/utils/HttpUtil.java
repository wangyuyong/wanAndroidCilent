package com.wyy.wanandroidcilent.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.wyy.wanandroidcilent.enity.BannerData;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class HttpUtil {

    //网络连接超时或失败的提示信息
    public static final String NO_INTERNET = "网络连接不良，请检查您的网络";

    //wan android登录api
    public static final String WAN_ANDROID_LOGIN_ADRESS = "https://www.wanandroid.com/user/login";

    //wan android注册api
    public static final String WAN_ANDROID_REGISTE_ADRESS = "https://www.wanandroid.com/user/register";

    //wan android banner api
    public static final String WAN_ANDROID_BANNER_ADRESS = "https://www.wanandroid.com/banner/json";


    /**
     * 发送Http请求(GET)
     * @param adress(Http地址)
     * @param listener(数据回调接口)
     */
    public static void sendHttpRequest(final String adress, final HttpCallBack listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    url = new URL(adress);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(8000); //设置连接超时时间
                    connection.setReadTimeout(8000);    //设置读写超时时间
                    connection.setRequestMethod("GET");

                    //包装输入流为缓冲流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {    //读取数据
                        builder.append(line);
                    }

                    //回调数据
                    if (listener != null) {
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    connection.disconnect();    //关闭连接
                }
            }
        }).start();
    }

    /**
     * 发送http请求(post)
     * @param adress(Http地址)
     * @param postData(参数)
     * @param listener(数据回调接口)
     */
    public static void sendHttpRequestByPost(final String adress, final String postData,final HttpCallBack listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    url = new URL(adress);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);    //设置读写超时时间
                    connection.setConnectTimeout(8000); //设置连接超时时间

                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    output.writeBytes(postData);    //写出数据
                    output.flush();
                    output.close();                 //保证数据全部写出

                    //包装输入流为缓冲流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    //读取数据
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }

                    //回调数据
                    if(listener != null){
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    //关闭连接
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
