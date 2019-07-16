package com.wyy.wanandroidcilent.ui.login;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Validation;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class LoginPresent implements LoginContract.LoginPresent {

    private LoginContract.LoginView view;
    private LoginContract.LoginModel model;

    public LoginPresent(){
        model = new LoginModel();
    }

    //处理登录逻辑
    @Override
    public void login() {
        final String userName = view.getUserName();
        final String password = view.getUserPassword();
        //显示加载条
        view.showLoading();
        //访问网络，获得验证信息
        model.requestLogin(userName,password)
                .subscribe(new Consumer<Validation>() {
                    @Override
                    public void accept(Validation validation) throws Exception {
                        String message = validation.getErrorMsg();
                        if ("".equals(message)) {
                            saveUserInformation(userName,password);
                            view.loginSuccess();
                        } else {
                            view.hideLoading();
                            view.loginError(message);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    //将用户名和密码保存至本地，用于下次自动登录
    @Override
    public void saveUserInformation(String userName,String password) {
        model.save(userName,password);
    }

    @Override
    public void bindView(LoginContract.LoginView view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        view = null;
    }
}
