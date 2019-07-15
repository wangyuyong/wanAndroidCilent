package com.wyy.wanandroidcilent.ui.registe;

import com.wyy.wanandroidcilent.base.BasePresent;
import com.wyy.wanandroidcilent.base.BaseView;

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
}
