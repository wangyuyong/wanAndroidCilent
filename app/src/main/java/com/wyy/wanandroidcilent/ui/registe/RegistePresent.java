package com.wyy.wanandroidcilent.ui.registe;

import com.wyy.wanandroidcilent.enity.Validation;

import io.reactivex.functions.Consumer;

public class RegistePresent implements RegisteContract.RegistePresent {
    RegisteContract.RegisteView view;
    RegisteContract.RegisteModel model;

    public RegistePresent(){
        model = new RegisteModel();
    }

    //注册逻辑
    @Override
    public void registe() {
        String userName = view.getUserName();
        String password = view.getPassword();
        String repassword = view.getRepassword();
        //发送注册信息
        model.requestRegiste(userName,password,repassword)
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
