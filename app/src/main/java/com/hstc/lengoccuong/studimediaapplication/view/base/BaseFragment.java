package com.hstc.lengoccuong.studimediaapplication.view.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hstc.lengoccuong.studimediaapplication.R;
import com.hstc.lengoccuong.studimediaapplication.presenter.BasePresenter;

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {
    public static final String TAG = BaseFragment.class.getName();
    protected T mPresenter;
    protected Context mContext;
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mContext = getActivity();
        mPresenter = getPresenter();
        initView();
        return mRootView;
    }

    protected abstract T getPresenter();

    protected abstract void initView();

    protected abstract int getLayoutId();

    public <G extends View> G findViewById(int idView) {
        return findViewById(idView, null);
    }

    public <G extends View> G findViewById(int idView,
                                           View.OnClickListener event) {
        G view = mRootView.findViewById(idView);
        if (event != null) {
            view.setOnClickListener(event);
        }
        return view;
    }


    protected void showNotify(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    protected void showNotify(int text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }
}
