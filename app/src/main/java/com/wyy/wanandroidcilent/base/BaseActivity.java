package com.wyy.wanandroidcilent.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.wyy.wanandroidcilent.R;

public class BaseActivity extends AppCompatActivity {
    public void addFragmentToLayout(int id,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.commit();
    }

    public void addFragmentToLayoutWithStack(int id,Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
