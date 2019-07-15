package com.wyy.wanandroidcilent.ui.registe;

import android.app.Notification;

import com.wyy.wanandroidcilent.enity.Validation;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class RegistePresent implements RegisteContract.RegistePresent {
    RegisteContract.RegisteView view;

    //注册逻辑
    @Override
    public void registe() {
        RetrofitManager registeManager = RetrofitManager.getInstance();
        HttpService registeService = registeManager.getService();
        //发送注册信息
        registeService.getValidation(view.getUserName(),view.getPassword(),view.getRepassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Validation>() {
                    @Override
                    public void accept(Validation validation) throws Exception {
                        //验证返回的注册信息
                        String message = validation.getErrorMsg();
                        if ("".equals(message)) {
                            view.registeSuccess();
                        } else {
                            view.registeError(message);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void bindView(RegisteContract.RegisteView view) {
        this.view = view;
    }

    @Override
    public void unBind() {
        view = null;
    }
}
