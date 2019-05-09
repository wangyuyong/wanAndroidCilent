package com.wyy.wanandroidcilent.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Banner;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {

    public static final String WAN_ANDROID_LOGIN_ADRESS = "https://www.wanandroid.com/user/login";
    public static final String WAN_ANDROID_REGISTE_ADRESS = "https://www.wanandroid.com/user/register";
    public static final String WAN_ANDROID_BANNER_ADRESS = "https://www.wanandroid.com/banner/json";

    /**
     * @description 发送Http请求(GET)
     * @param adress(Http地址)
     * @param listener(数据回调接口)
     */
    public static void sendHttpRequest(final String adress, final HttpCallBack listener){
        if(!StateUtil.isNetworkConnected(MyApplication.getContext())){
            Toast.makeText(MyApplication.getContext(),"网络连接不良，请检查您的网络设置",Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;

                try {
                    url = new URL(adress);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestMethod("GET");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    connection.disconnect();
                }
            }
        }).start();
    }

    /**
     *
     * @param adress(Http地址)
     * @param postData(post的数据)
     * @param listener(数据回调接口)
     */
    public static void sendHttpRequestByPost(final String adress, final String postData,final HttpCallBack listener){
        if(!StateUtil.isNetworkConnected(MyApplication.getContext())){
            Toast.makeText(MyApplication.getContext(),"网络连接不良，请检查您的网络设置",Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpURLConnection connection = null;
                try {
                    url = new URL(adress);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                    output.writeBytes(postData);
                    output.flush();
                    output.close();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(builder.toString());
                    }
                } catch (Exception e) {
                    if(listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void loadPictureFromNet(final List<Banner> banners, final List<Bitmap> bitmaps){
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                HttpsURLConnection connection = null;
                try {
                    for(int i =0; i < banners.size(); i++) {                                        //将banners转为为对应的图片
                        url = new URL(banners.get(i).getImagePath());
                        connection = (HttpsURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setDoInput(true);
                        InputStream input = connection.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(input);
                        bitmaps.add(bitmap);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
