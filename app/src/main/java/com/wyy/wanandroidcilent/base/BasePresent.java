package com.wyy.wanandroidcilent.base;

//present的基类
public interface BasePresent<T extends BaseView> {

    //绑定view
    void bindView(T view);

    //解除绑定，避免内存泄漏
    void unBind();
}
