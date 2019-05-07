package com.wyy.wanandroidcilent.utils;

import android.widget.Toast;

import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.net.HttpCallBack;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {

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
}
