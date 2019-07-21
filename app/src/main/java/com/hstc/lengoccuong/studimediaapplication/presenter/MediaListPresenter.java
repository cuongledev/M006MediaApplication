package com.hstc.lengoccuong.studimediaapplication.presenter;

import com.hstc.lengoccuong.studimediaapplication.view.event.OnMediaListCallBack;

public class MediaListPresenter extends BasePresenter<OnMediaListCallBack> {
    public MediaListPresenter(OnMediaListCallBack mCallBack) {
        super(mCallBack);
    }
}