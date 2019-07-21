package com.hstc.lengoccuong.studimediaapplication.manager;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.hstc.lengoccuong.studimediaapplication.model.MediaModel;
import com.hstc.lengoccuong.studimediaapplication.view.CAAplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaManager implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
    public static final int STATE_IDLE = 0;
    public static final int STATE_PLAYING = 1;
    public static final int STATE_PAUSED = 2;
    private static final MediaPlayer PLAYER = new MediaPlayer();
    private int mState = STATE_IDLE;
    private int mCurrentIndex;
    private OnStateChange mCallBack;

    public void setOnStateChange(OnStateChange event){
        mCallBack = event;
    }

    private static final String[] PROJECTION =  new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DISPLAY_NAME};
    private static MediaManager INSTANCE;
    private List<MediaModel> mListMedia;

    private MediaManager(){
        // for singleton
        init();
    }

    public void play(){
        if (mState==STATE_IDLE){
            PLAYER.reset();
            Uri path = Uri.parse(mListMedia.get(mCurrentIndex).getData());
            try {
                PLAYER.setDataSource(CAAplication.getInstance(), path);
                PLAYER.prepare();
                PLAYER.start();
                updateState(STATE_PLAYING);
                //mState = STATE_PLAYING;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (mState == STATE_PLAYING){
            PLAYER.pause();
            updateState(STATE_PAUSED);
        }else if (mState == STATE_PAUSED){
            PLAYER.start();
            updateState(STATE_PLAYING);
        }

    }


    public void next(){
        if(mCurrentIndex>=mListMedia.size()-1){
            mCurrentIndex = -1;
        }
        mCurrentIndex++;
        stop();
        play();


    }
    public void previous(){
        if(mCurrentIndex<0){
            mCurrentIndex = mListMedia.size();
        }
        mCurrentIndex--;
        stop();
        play();


    }

    private void updateState(int state) {
        mState = state;
        mCallBack.stateChange(mState);
    }

    private void init(){
        PLAYER.setOnCompletionListener(this);
        PLAYER.setOnPreparedListener(this);
        PLAYER.setOnErrorListener(this);
        PLAYER.setOnBufferingUpdateListener(this);
    }
    public static MediaManager getInstance(){
        if (INSTANCE==null){
            INSTANCE = new MediaManager();
        }
        return INSTANCE;
    }

    public void getMedias(){
        mListMedia = new ArrayList<>();

        Cursor cursor = CAAplication
                .getInstance()
                .getContentResolver()
                .query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                       PROJECTION
                        ,null,null, MediaStore.Audio.Media.TITLE+" ASC"

                        );

        if(cursor==null) return;
        if(cursor.getCount()==0){
            cursor.close();
            return;
        }
        cursor.moveToFirst();
        int indexID = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        int indexTitle = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexAlbum = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int indexArtist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indexDisplayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
        while (!cursor.isAfterLast()){
            String id = cursor.getString(indexID);
            String title = cursor.getString(indexTitle);
            String album = cursor.getString(indexAlbum);
            String artist = cursor.getString(indexArtist);
            long duration = cursor.getLong(indexDuration);
            String data = cursor.getString(indexData);
            String displayName = cursor.getString(indexDisplayName);

            mListMedia.add(new MediaModel(id,title,album,artist,data,displayName,duration));

            cursor.moveToNext();
        }
        cursor.close();


    }

    public List<MediaModel> getListMedia(){
        return mListMedia;
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        next();
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        return false;
    }

    public void setCurrentIndex(MediaModel media) {
        mCurrentIndex = mListMedia.indexOf(media);
    }

    public long getCurrentTime() {
        return PLAYER.getCurrentPosition();
    }

    public void stop() {
        PLAYER.reset();
        updateState(STATE_IDLE);
    }

    public void seekTo(int progress) {
        PLAYER.seekTo(progress);
        updateState(mState);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        //Log.i(TAG,"Buffering: ");
    }

    public interface OnStateChange{
        void stateChange(int state);
    }

    public MediaModel getCurrentMedia(){
        return mListMedia.get(mCurrentIndex);
    }
}
