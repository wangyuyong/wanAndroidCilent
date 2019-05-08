package com.wyy.wanandroidcilent.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.net.HttpCallBack;
import com.wyy.wanandroidcilent.utils.StateUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    Button registerBtn;
    EditText userNameEd;
    EditText passwordEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();                                                                           //隐藏标题栏

        loginBtn = (Button)findViewById(R.id.btn_login);
        registerBtn = (Button)findViewById(R.id.btn_register);
        userNameEd = (EditText)findViewById(R.id.ed_username);
        passwordEd = (EditText)findViewById(R.id.ed_password);                                        //获取实例

        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                registe();
                break;
            default:
        }
    }

    private void login(){
        String  userName = userNameEd.getText().toString();
        String password = passwordEd.getText().toString();
        //登录逻辑
    }

    private void registe(){
       //注册逻辑
    }
}
