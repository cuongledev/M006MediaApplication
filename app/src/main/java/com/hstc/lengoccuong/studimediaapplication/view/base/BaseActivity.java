package com.hstc.lengoccuong.studimediaapplication.view.base;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hstc.lengoccuong.studimediaapplication.presenter.BasePresenter;

import java.lang.reflect.Constructor;

public abstract class BaseActivity<T extends BasePresenter>
        extends AppCompatActivity {
    protected T mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mPresenter = getPresenter();
        initView();
    }

    protected abstract T getPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public <G extends View> G findViewById(int idView,
                                           View.OnClickListener event) {
        G view = findViewById(idView);
        if (view instanceof TextView && event != null) {
            view.setOnClickListener(event);
        }
        return view;
    }


    protected void showNotify(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void showNotify(int text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void showFragment(String tag){
        try {
            Class<?> clazz = Class.forName(tag);
            Constructor<?> constructor
                    = clazz.getConstructor();
            BaseFragment frg
                    = (BaseFragment) constructor.newInstance();

            getSupportFragmentManager().beginTransaction()
                    .replace(getContentId(), frg).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract int getContentId();
}
