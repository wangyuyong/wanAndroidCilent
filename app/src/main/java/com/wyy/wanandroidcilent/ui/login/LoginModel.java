package com.wyy.wanandroidcilent.ui.login;

import com.wyy.wanandroidcilent.Const;
import com.wyy.wanandroidcilent.app.MyApplication;
import com.wyy.wanandroidcilent.enity.Validation;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;
import com.wyy.wanandroidcilent.utils.SharedPreferencesUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.LoginModel {

    HttpService service;

    public LoginModel(){
        RetrofitManager manager = RetrofitManager.getInstance();
        service = manager.getService();
    }

    @Override
    public Observable<Validation> requestLogin(String userName, String password) {
        return service.getValidation(userName,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void save(String userName, String password) {
        SharedPreferencesUtil.outputWithSharePreference(MyApplication.getContext(),
                Const.CONST_USER,Const.CONST_USER_NAME,userName);
        SharedPreferencesUtil.outputWithSharePreference(MyApplication.getContext(),
                Const.CONST_USER,Const.CONST_PASSWORD,password);
    }
}
