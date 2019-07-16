package com.wyy.wanandroidcilent.ui.registe;

import com.wyy.wanandroidcilent.enity.Validation;
import com.wyy.wanandroidcilent.net.HttpService;
import com.wyy.wanandroidcilent.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegisteModel implements RegisteContract.RegisteModel {

    HttpService service;

    public RegisteModel(){
        service = RetrofitManager.getInstance()
                .getService();
    }

    @Override
    public Observable<Validation> requestRegiste(String userName, String password, String repassword) {
        return service.getValidation(userName,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
