package com.hstc.lengoccuong.studimediaapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private static CommonUtils INSTANCE;

    private CommonUtils() {
        //for singleton
    }

    public static CommonUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommonUtils();
        }
        return INSTANCE;
    }

    public static String getTimeMedia(long time) {
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        return df.format(new Date(time));
    }
}
