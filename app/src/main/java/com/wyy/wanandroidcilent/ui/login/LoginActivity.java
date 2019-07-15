package com.wyy.wanandroidcilent.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wyy.wanandroidcilent.R;
import com.wyy.wanandroidcilent.base.BaseActivity;
import com.wyy.wanandroidcilent.ui.page.PageActivity;
import com.wyy.wanandroidcilent.ui.registe.RegisteActivity;
import com.wyy.wanandroidcilent.utils.StateUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    LoginContract.LoginPresent present;

    @BindView(R.id.pb_login)
    ProgressBar loginPb;
    @BindView(R.id.edt_username)
    EditText userNameEdt;
    @BindView(R.id.til_username)
    TextInputLayout userNameTil;
    @BindView(R.id.edt_password)
    EditText passwordEdt;
    @BindView(R.id.til_password)
    TextInputLayout passwordTil;
    @BindView(R.id.btn_login)
    Button loginBtn;
    @BindView(R.id.btn_register)
    Button registeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //present绑定view
        present = new LoginPresent();
        present.bindView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除present的绑定
        present.unBind();
    }

    //获得用户名
    @Override
    public String getUserName() {
        return userNameEdt.getText().toString();
    }

    //获得登录密码
    @Override
    public String getUserPassword() {
        return passwordEdt.getText().toString();
    }

    //显示加载条
    @Override
    public void showLoading() {
        loginPb.setVisibility(View.VISIBLE);
    }

    //隐藏加载条
    @Override
    public void hideLoading() {
        loginPb.setVisibility(View.GONE);
    }

    //登录成功
    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, PageActivity.class);
        startActivity(intent);
        finish();
    }

    //登录失败
    @Override
    public void loginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    //打开注册界面
    @Override
    public void openRegiste() {
        Intent intent = new Intent(this, RegisteActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (StateUtil.isFastClicked()){
                    return;
                }
                present.login();
                break;
            case R.id.btn_register:
                if (StateUtil.isFastClicked()){
                    return;
                }
                openRegiste();
                break;
        }
    }
}
