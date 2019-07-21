package com.hstc.lengoccuong.studimediaapplication.view;

import android.app.Application;

public class CAAplication  extends Application {
    private static CAAplication INSTANCE;
    public CAAplication(){
        INSTANCE = this;
    }
    public static CAAplication getInstance(){
        return INSTANCE;
    }
}
