package com.wyy.wanandroidcilent.utils;

import android.content.Context;
import android.content.SharedPreferences;

//用sharedPreferences存储数据
public class SharedPreferencesUtil {

    public static final String HAVE_READ_FILE = "have_read";            //已读文章的文章名存储的文件夹
    public static final String COOKIE_FILE = "cookie";                  //存放cookie的文件夹

    /**
     *
     * @param context
     * @param file(存储的路径)
     * @param key(键)
     * @param value(键值)
     */
    public static void outputWithSharePreference(Context context,String file,String key,boolean value){
        SharedPreferences output = context.getSharedPreferences(file,0);
        SharedPreferences.Editor editor = output.edit();
        editor.putBoolean(key,value);
        editor.apply();
    }
    public static void outputWithSharePreference(Context context,String file,String key,String value){
        SharedPreferences output = context.getSharedPreferences(file,0);
        SharedPreferences.Editor editor = output.edit();
        editor.putString(key,value);
        editor.apply();
    }
    /**
     *
     * @param context
     * @param file
     * @param key
     * @return 如果在文件中找不到key所对应的值，返回false,否则，返回key对应的值
     */
    public static boolean getBooleanWithSharePreference(Context context,String file,String key){
        SharedPreferences input = context.getSharedPreferences(file,0);
        return input.getBoolean(key,false);
    }

    public static String getStringWithSharePreference(Context context,String file,String key){
        SharedPreferences input = context.getSharedPreferences(file,0);
        return input.getString(key,"");
    }
}
