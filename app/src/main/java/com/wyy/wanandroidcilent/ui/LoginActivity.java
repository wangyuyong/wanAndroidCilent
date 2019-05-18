package com.wyy.wanandroidcilent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button)findViewById(R.id.btn_login);
        registerBtn = (Button)findViewById(R.id.btn_register);
        userNameEd = (EditText)findViewById(R.id.ed_username);
        passwordEd = (EditText)findViewById(R.id.ed_password);
        loginPb = (ProgressBar)findViewById(R.id.pb_login);     //获取实例

        loginBtn.setOnClickListener(this);      //为登录按钮添加监听
        registerBtn.setOnClickListener(this);   //为注册按钮添加监听
    }

    @Override
    public void onClick(View v) {
        if (StateUtil.isFastClicked()){
            return;
        }
        switch (v.getId()){
            case R.id.btn_login:
                loginPb.setVisibility(View.VISIBLE);    //显示ProssBar
                login();                                //登录验证
                break;
            case R.id.btn_register:
                registe();                              //用户注册
                break;
            default:
        }
    }

    private void login(){
        final String  userName = userNameEd.getText().toString();   //获得用户名
        final String password = passwordEd.getText().toString();    //获得用户密码

        //没有网络则报错
        if(!StateUtil.isNetworkConnected(this)){
            loginPb.setVisibility(View.GONE);
            Toast.makeText(this,StateUtil.NET_STATUS,Toast.LENGTH_LONG).show();
            return;
        }

        //发送网络请求
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
                            //身份验证成功
                            if (errorCode == 0){
                                //将用户名和密码存入本地
                                SharedPreferencesUtil.outputWithSharePreference(LoginActivity.this,
                                        SharedPreferencesUtil.USER,"username",userName);
                                SharedPreferencesUtil.outputWithSharePreference(LoginActivity.this,
                                        SharedPreferencesUtil.USER,"password",password);

                                //向下一活动传送用户名
                                Intent intent = new Intent(LoginActivity.this,
                                        HomePageActivity.class);
                                intent.putExtra("username",userName);
                                startActivity(intent);                //完成登录
                                finish();
                            }else {                                  //验证失败，提示用户
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
                tipNoInternet();            //提示用户网络连接超时
                e.printStackTrace();
            }
        });
    }

    private void registe(){         //打开注册界面
       Intent intent = new Intent(this,RegisteActivity.class);
       startActivity(intent);
    }
}
