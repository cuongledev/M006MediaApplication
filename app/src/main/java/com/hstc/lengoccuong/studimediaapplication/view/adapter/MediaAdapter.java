package com.hstc.lengoccuong.studimediaapplication.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hstc.lengoccuong.studimediaapplication.R;
import com.hstc.lengoccuong.studimediaapplication.model.MediaModel;

import java.util.List;

public class MediaAdapter extends RecyclerView.Adapter {
    private final List<MediaModel> mData;
    private final Context mContext;
    private MediaModel selectMedia;
    public MediaAdapter(Context context,
                        List<MediaModel> listMedia) {
        mData = listMedia;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MediaHolder(View.inflate(mContext,
                R.layout.item_media, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        MediaHolder holder = (MediaHolder) viewHolder;
        MediaModel data = mData.get(pos);

        if (data.isSelect()){
            holder.lnMedia.setBackgroundResource(R.color.grayLight);
        }else{
            holder.lnMedia.setBackgroundResource(R.color.colorWhite);
        }

        holder.tvTitle.setText(data.getTitle());
        holder.tvArtist.setText(data.getArtist());
        holder.tvTitle.setTag(data);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateSelectMedia(MediaModel media){
        if(selectMedia!=null){
            selectMedia.setSelect(false);
        }
        selectMedia = media;
        selectMedia.setSelect(true);
        notifyDataSetChanged();
    }

    private class MediaHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvArtist;
        View lnMedia;

        public MediaHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            lnMedia = itemView.findViewById(R.id.ln_media);
            lnMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //lnMedia.setBackgroundResource(R.color.grayLight);
                    if(selectMedia!=null){
                        selectMedia.setSelect(false);
                    }
                    selectMedia = (MediaModel) tvTitle.getTag();
                    selectMedia.setSelect(true);

                    notifyDataSetChanged();
                    mCallBack.selectItem(selectMedia);


                }
            });
        }
    }
    private OnItemSelect mCallBack;
    public void setOnItemSelect(OnItemSelect event){
        mCallBack = event;
    }
    public interface OnItemSelect{
        void selectItem(MediaModel media);
    }
}