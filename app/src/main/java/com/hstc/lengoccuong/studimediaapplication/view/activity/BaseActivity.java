package com.hstc.lengoccuong.studimediaapplication.view.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hstc.lengoccuong.studimediaapplication.presenter.BasePresenter;
import com.hstc.lengoccuong.studimediaapplication.view.base.BaseFragment;

import java.lang.reflect.Constructor;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
    }

    protected abstract int getLayoutId();
    protected abstract void initView();

    public void showFragment(String tag){
        try{
            Class<?> clazz = Class.forName(tag);
            Constructor<?> constructor = clazz.getConstructor();
            BaseFragment frg = (BaseFragment) constructor.newInstance();
            getSupportFragmentManager().beginTransaction().replace(getContentId(),frg).commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected abstract int getContentId();
}
