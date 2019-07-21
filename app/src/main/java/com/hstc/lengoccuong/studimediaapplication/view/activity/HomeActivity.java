package com.hstc.lengoccuong.studimediaapplication.view.activity;

import android.content.pm.PackageManager;
import android.util.Log;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.hstc.lengoccuong.studimediaapplication.R;
import com.hstc.lengoccuong.studimediaapplication.manager.MediaManager;
import com.hstc.lengoccuong.studimediaapplication.presenter.HomePresenter;
import com.hstc.lengoccuong.studimediaapplication.view.event.OnHomeCallBack;
import com.hstc.lengoccuong.studimediaapplication.view.fragment.MediaListFragment;


public class HomeActivity extends BaseActivity<HomePresenter>
        implements OnHomeCallBack {
    private static final int KEY_REQUEST_PERMISSION = 101;
    private static final String TAG = HomeActivity.class.getName();

    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    protected void initView() {
        if (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    KEY_REQUEST_PERMISSION);
        }else {
            initData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            initData();
        }
    }

    private void initData() {
        Log.i(TAG, "initData...");
        MediaManager.getInstance().getMedias();

        Log.i(TAG, "List of medias: "
                + MediaManager.getInstance().getListMedia());

        showFragment(MediaListFragment.TAG);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_home;
    }

    @Override
    protected int getContentId() {
        return R.id.ln_content;
    }
}
