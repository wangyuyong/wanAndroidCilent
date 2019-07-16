package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.os.Bundle;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.ui.page.PageActivity;
import com.wyy.wanandroidcilent.ui.login.LoginActivity;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

//选择进入loginActivity或HomePageActivity
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userName = SharedPreferencesUtil.getStringWithSharePreference(this,
                Const.CONST_USER,"username");
        String password = SharedPreferencesUtil.getStringWithSharePreference(this,
                Const.CONST_USER,"password");

        //本地中不存在用户信息，进入登录界面
        if("".equals(userName) && "".equals(password)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            //本地中存在用户信息，进入主界面
            Intent intent = new Intent(this, PageActivity.class);
            //向下一活动传送用户名
            intent.putExtra("username",userName);
            startActivity(intent);
            finish();
        }
    }
}
