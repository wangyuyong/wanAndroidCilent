package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String userName = SharedPreferencesUtil.getStringWithSharePreference(this,
                SharedPreferencesUtil.USER,"username");
        String password = SharedPreferencesUtil.getStringWithSharePreference(this,
                SharedPreferencesUtil.USER,"password");

        if("".equals(userName) && "".equals(password)){                                             //本地中不存在用户信息，进入登录界面
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else{                                                                                      //本地中存在用户信息，进入主界面
            autoLogin(userName,password);                                                           //自动验证身份信息
            Intent intent = new Intent(this,HomePageActivity.class);
            intent.putExtra("username",userName);                                           //向下一活动传送用户名
            startActivity(intent);
            finish();
        }
    }

    private void autoLogin(String userName,String password){
        HttpUtil.sendHttpRequestByPost(HttpUtil.WAN_ANDROID_LOGIN_ADRESS, "username=" +
                userName + "&password=" + password, new HttpCallBack() {
            @Override
            public void onFinish(String respone) {
                try {
                    JSONObject object = new JSONObject(respone);
                    int errorCode = object.getInt("errorCode");
                    if (errorCode != 0){                                                           //身份验证失败，返回登录界面
                        Toast.makeText(MainActivity.this,"身份验证失败，请重新登录",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
