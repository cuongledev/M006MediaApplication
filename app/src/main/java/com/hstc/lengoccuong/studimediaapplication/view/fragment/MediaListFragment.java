package com.hstc.lengoccuong.studimediaapplication.view.fragment;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hstc.lengoccuong.studimediaapplication.R;
import com.hstc.lengoccuong.studimediaapplication.manager.MediaManager;
import com.hstc.lengoccuong.studimediaapplication.model.MediaModel;
import com.hstc.lengoccuong.studimediaapplication.presenter.MediaListPresenter;
import com.hstc.lengoccuong.studimediaapplication.utils.CommonUtils;
import com.hstc.lengoccuong.studimediaapplication.view.adapter.MediaAdapter;
import com.hstc.lengoccuong.studimediaapplication.view.base.BaseFragment;
import com.hstc.lengoccuong.studimediaapplication.view.event.OnMediaListCallBack;
import com.hstc.lengoccuong.studimediaapplication.view.widget.SeekBarChangeAdapter;

import java.text.BreakIterator;

public class MediaListFragment
        extends BaseFragment<MediaListPresenter>
        implements OnMediaListCallBack, View.OnClickListener, MediaManager.OnStateChange, MediaAdapter.OnItemSelect {
    public static final String TAG = MediaListFragment.class.getName();
    private RecyclerView mRvMedia;
    public ImageView mIvPlay;
    private static final int LEVEL_PLAYING = 1;
    private static final int LEVEL_PAUSED = 0;
    private TextView mTvTitle;
    private TextView mTvEndTime;
    private TextView mTvStartTime;
    private SeekBar mSeekBar;

    private SeekBarTask mSeekBarTask;

    @Override
    protected MediaListPresenter getPresenter() {
        return new MediaListPresenter(this);
    }

    @Override
    protected void initView() {
        mRvMedia = findViewById(R.id.rv_media);
        mRvMedia.setLayoutManager(new LinearLayoutManager(mContext));
        MediaAdapter adapter = new MediaAdapter(mContext,
                MediaManager.getInstance().getListMedia());
        adapter.setOnItemSelect(this);
        mRvMedia.setAdapter(adapter);
        mIvPlay = findViewById(R.id.iv_play, this);
        mTvTitle = findViewById(R.id.tv_title, this);
        mTvEndTime = findViewById(R.id.tv_total, this);
        mTvStartTime = findViewById(R.id.tv_start, this);
        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBarChangeAdapter() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MediaManager.getInstance().seekTo(seekBar.getProgress());
            }
        });
        findViewById(R.id.iv_next, this);
        findViewById(R.id.iv_previous, this);

        MediaManager.getInstance().setOnStateChange(this);
        mSeekBarTask = new SeekBarTask();
        mSeekBarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        updateUI();


    }

    private void updateUI() {
        MediaModel media = MediaManager.getInstance().getCurrentMedia();
        mTvTitle.setText(media.getTitle());
        mTvEndTime.setText(CommonUtils.getInstance()
                .getTimeMedia(media.getDuration()));
        mSeekBar.setMax((int) media.getDuration());
        mTvStartTime.setText(CommonUtils.getInstance()
                .getTimeMedia(MediaManager.getInstance().getCurrentTime()));
        ((MediaAdapter)mRvMedia.getAdapter()).updateSelectMedia(media);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_media_list;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                MediaManager.getInstance().play();
                break;
            case R.id.iv_next:
                MediaManager.getInstance().next();
                break;
            case R.id.iv_previous:
                MediaManager.getInstance().previous();
                break;

            default:
                break;
        }

    }

    @Override
    public void stateChange(int state) {
        switch (state){
            case MediaManager.STATE_PLAYING:
            case MediaManager.STATE_IDLE:
                mIvPlay.setImageLevel(LEVEL_PLAYING);
                updateUI();

                break;
            case MediaManager.STATE_PAUSED:
                mIvPlay.setImageLevel(LEVEL_PAUSED);
                break;
        }
    }

    @Override
    public void selectItem(MediaModel media) {
        MediaManager.getInstance().setCurrentIndex(media);
        MediaManager.getInstance().stop();
        MediaManager.getInstance().play();
    }
    
    private class SeekBarTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            while (true){
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return null;
                }
                publishProgress();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mTvStartTime.setText(CommonUtils.getInstance()
                    .getTimeMedia(MediaManager.getInstance().getCurrentTime()));
            mSeekBar.setProgress((int) MediaManager.getInstance().getCurrentTime());
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSeekBarTask.cancel(true);
        MediaManager.getInstance().stop();
    }
}
