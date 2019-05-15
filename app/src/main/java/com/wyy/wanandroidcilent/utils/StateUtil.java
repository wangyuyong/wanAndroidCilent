package com.wyy.wanandroidcilent.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class StateUtil {

    //网络不良的提示信息
    public static final String NET_STATUS = "网络连接不良，请检查您的网络设置";
    private static final int CLICK_LIMIT_TIME = 1000;
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

    public static Boolean isFastClicked(){
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < CLICK_LIMIT_TIME){
            lastClickTime = clickTime;
            return true;
        }else {
            lastClickTime = clickTime;
            return false;
        }
    }
}
