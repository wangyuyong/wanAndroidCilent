package com.wyy.wanandroidcilent.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.utils.HttpUtil;

public class BaseActivity extends AppCompatActivity {

    //输出提示信息
    public void tipNoInternet(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, HttpUtil.NO_INTERNET,Toast.LENGTH_LONG).show();
            }
        });
    }
    //为某一容器中添加碎片
    public void addFragmentToLayout(int id,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.commit();
    }

    //以返回栈的形式为某一容器添加碎片
    public void addFragmentToLayoutWithStack(int id,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
