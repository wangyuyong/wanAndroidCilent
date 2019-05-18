package com.wyy.wanandroidcilent.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.wyy.wanandroidcilent.app.MyApplication;

public class StateUtil {

    //网络不良的提示信息
    public static final String NET_STATUS = "网络连接不良，请检查您的网络设置";

    //每次刷新的时间间隔
    private static final int REFRESH_LIMIT_TIME = 1000;

    //每次点击的时间间隔
    private static final int CLICK_LIMIT_TIME = 500;

    //最后一次刷新的时间
    private static long lastRefresh;

    //最后一次点击的时间
    private static long lastClickTime;

    /**
     * 判断是否有网络
     * @param context
     * @return
     */
    public static Boolean isNetworkConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null){
            return info.isConnected();
        }
        return false;
    }

    /**
     * 是否过快点击
     * @return
     */
    public static Boolean isFastClicked(){
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < CLICK_LIMIT_TIME){
            Toast.makeText(MyApplication.getContext(),"不要点那么快！",Toast.LENGTH_SHORT).show();
            lastClickTime = clickTime;
            return true;
        }else {
            lastClickTime = clickTime;
            return false;
        }
    }

    /**
     * 是否过快刷新
     * @return
     */
    public static Boolean isFastRefresh(){
        long refreshTime = System.currentTimeMillis();
        if (refreshTime - lastRefresh < REFRESH_LIMIT_TIME){
            lastRefresh = refreshTime;
            Toast.makeText(MyApplication.getContext(),"不要着急，等一会再刷新！",Toast.LENGTH_SHORT).show();
            return true;
        }else {
            lastRefresh = refreshTime;
            return false;
        }
    }
}
