package com.wyy.wanandroidcilent.ui.login;

import com.wyy.wanandroidcilent.base.BaseModel;
import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;
import com.wyy.wanandroidcilent.enity.Validation;

import io.reactivex.Observable;

public interface LoginContract {

    interface LoginView extends BaseView {

        //获得用户名
        String getUserName();

        //获得用户密码
        String getUserPassword();

        //展示加载条
        void showLoading();

        //隐藏加载条
        void hideLoading();

        //登录成功
        void loginSuccess();

        //登录失败
        void loginError(String message);

        //打开注册页面
        void openRegiste();
    }

    interface LoginPresent extends BasePresent<LoginView> {

        //登录
        void login();

        //保存登录信息
        void saveUserInformation(String userName,String password);
    }

    interface LoginModel extends BaseModel{
        //请求登录
        Observable<Validation> requestLogin(String userName, String password);

        //保存用户信息
        void save(String userName,String password);
    }
}
