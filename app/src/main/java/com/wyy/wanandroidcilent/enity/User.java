package com.wyy.wanandroidcilent.enity;

import java.io.Serializable;
import java.net.URL;

public class User implements Serializable {

    private String userName;
    private String password;
    private boolean login;                                                                          //用户登录状态

    public User(String userName,String password){
        this.userName = userName;
        this.password = password;
        login = false;
    }

    public User(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
