package com.wyy.wanandroidcilent.utils;

import android.content.Context;
import android.content.SharedPreferences;

//用sharedPreferences存储数据
public class SharedPreferencesUtil {

    /**
     * 将key-value存入file
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
     * 从file中取出key对应的布尔值
     * @param context
     * @param file
     * @param key
     * @return 如果在文件中找不到key所对应的值，返回false,否则，返回key对应的值
     */
    public static boolean getBooleanWithSharePreference(Context context,String file,String key){
        SharedPreferences input = context.getSharedPreferences(file,0);
        return input.getBoolean(key,false);
    }

    /**
     * 从file中取出key对应的字符串
     * @param context
     * @param file
     * @param key
     * @return
     */
    public static String getStringWithSharePreference(Context context,String file,String key){
        SharedPreferences input = context.getSharedPreferences(file,0);
        return input.getString(key,"");
    }
    /**
     * 删除file中key - value
     * @param context
     * @param file
     * @param key
     */
    public static void deleteWithSharePreference(Context context,String file,String key){
        SharedPreferences output = context.getSharedPreferences(file,0);
        SharedPreferences.Editor editor = output.edit();
        editor.remove(key);
        editor.apply();
    }
}
