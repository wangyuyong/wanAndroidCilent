package com.wyy.wanandroidcilent.ui.registe;

import com.wyy.wanandroidcilent.base.BaseModel;
import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;
import com.wyy.wanandroidcilent.enity.Validation;

import io.reactivex.Observable;

public interface RegisteContract {
    interface RegisteView extends BaseView{

        //获得用户名
        String getUserName();

        //获得密码
        String getPassword();

        //获得确认密码
        String getRepassword();

        //注册成功
        void registeSuccess();

        //注册失败
        void registeError(String message);

        //取消注册
        void cancel();
    }

    interface RegistePresent extends BasePresent<RegisteView>{

        //注册逻辑
        void registe();
    }

    interface RegisteModel extends BaseModel{

        //发送请求注册信息
        Observable<Validation> requestRegiste(String userName,String password,String repassword);
    }
}
