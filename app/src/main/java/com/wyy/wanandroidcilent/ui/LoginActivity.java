package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.HttpUtil;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;
import com.wyy.wanandroidcilent.utils.StateUtil;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    Button loginBtn;
    Button registerBtn;
    EditText userNameEd;
    EditText passwordEd;
    ProgressBar loginPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();                                                                           //隐藏标题栏

        loginBtn = (Button)findViewById(R.id.btn_login);
        registerBtn = (Button)findViewById(R.id.btn_register);
        userNameEd = (EditText)findViewById(R.id.ed_username);
        passwordEd = (EditText)findViewById(R.id.ed_password);
        loginPb = (ProgressBar)findViewById(R.id.pb_login);                                          //获取实例

        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                loginPb.setVisibility(View.VISIBLE);
                login();
                break;
            case R.id.btn_register:
                registe();
                break;
            default:
        }
    }

    private void login(){
        final String  userName = userNameEd.getText().toString();
        final String password = passwordEd.getText().toString();

        if(!StateUtil.isNetworkConnected(this)){
            loginPb.setVisibility(View.GONE);
            Toast.makeText(this,StateUtil.NET_STATUS,Toast.LENGTH_LONG).show();
            return;
        }

        HttpUtil.sendHttpRequestByPost(HttpUtil.WAN_ANDROID_LOGIN_ADRESS, "username="
                + userName + "&password=" + password, new HttpCallBack() {
            @Override
            public void onFinish(String respone) {
                try {
                    JSONObject object = new JSONObject(respone);
                    final int errorCode = object.getInt("errorCode");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (errorCode == 0){                                                    //验证成功
                                SharedPreferencesUtil.outputWithSharePreference(LoginActivity.this,
                                        SharedPreferencesUtil.USER,"username",userName);
                                SharedPreferencesUtil.outputWithSharePreference(LoginActivity.this,
                                        SharedPreferencesUtil.USER,"password",password);       //将用户名和密码存入本地
                                Intent intent = new Intent(LoginActivity.this,
                                        HomePageActivity.class);
                                intent.putExtra("username",userName);                       //向下一活动传送用户名
                                startActivity(intent);                                              //完成登录
                                finish();
                            }else {                                                                 //验证失败，提示用户
                                loginPb.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,"账号密码不匹配",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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

    private void registe(){
       Intent intent = new Intent(this,RegisteActivity.class);
       startActivity(intent);
    }
}
