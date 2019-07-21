package com.hstc.lengoccuong.studimediaapplication.presenter;

import com.hstc.lengoccuong.studimediaapplication.view.event.OnCallBackToView;

public abstract class BasePresenter<T extends OnCallBackToView> {
    protected T mCallBack;

    public BasePresenter(T mCallBack) {
        this.mCallBack = mCallBack;
    }
}
