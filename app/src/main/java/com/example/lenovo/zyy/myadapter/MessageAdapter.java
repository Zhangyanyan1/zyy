package com.example.lenovo.zyy.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.collback.MessRecyclerItemClick;
import com.example.lenovo.zyy.holder.ImageHolder;
import com.example.lenovo.zyy.holder.TextHolder;
import com.example.lenovo.zyy.holder.VideoHolder;
import com.example.lenovo.zyy.holder.VoiceHolder;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/5/9.
 */

public class MessageAdapter extends RecyclerView.Adapter {
    private MessRecyclerItemClick click;
    private Context context;
    private ArrayList<EMMessage> list;

    public MessageAdapter(Context context, ArrayList<EMMessage> list) {
        this.context = context;
        this.list = list;
    }

    /**
     *根据消息类型 匹配item的type
     * @param position item的下标
     * @return         item的type
     */
    @Override
    public int getItemViewType(int position) {
        EMMessage msg = list.get (position);
       switch (msg.getType ()){
           case TXT:
               return 0;
           case IMAGE:
               return 1;
           case VIDEO:
               return 2;
           case VOICE:
               return 3;

       }
        return -1;
    }

    /**
     * 根据item的type创建不同的ViewHolder
     * @param parent
     * @param viewType   item的type==getItemViewType（）的返回值
     * @return    ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new TextHolder (LayoutInflater.from (context).inflate (R.layout.item_message, parent, false));
            case 1:
                return new ImageHolder (LayoutInflater.from (context).inflate (R.layout.item_image_views, parent, false));
            case 2:
                return new VideoHolder (LayoutInflater.from (context).inflate (R.layout.item_video_views, parent, false));
            case 3:
                return new VoiceHolder (LayoutInflater.from (context).inflate (R.layout.item_voice_views, parent, false));
        }
//        默认的ViewHolder
        return new TextHolder (LayoutInflater.from (context).inflate (R.layout.item_message, parent, false));
    }

    /**
     * 给ViewHolder设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        获取当前item的数据
        EMMessage item = list.get (position);
//        判断当前item的ViewHolder是什么类型
        if (holder instanceof TextHolder){
//        强转并调用设置数据的方法
            ((TextHolder) holder).setView (context,item);
        }else if (holder instanceof ImageHolder){
            ((ImageHolder) holder).setView (context,item);
        }else if (holder instanceof VoiceHolder){
            ((VoiceHolder) holder).setView (item);
        }else if (holder instanceof VideoHolder){
            ((VideoHolder) holder).setView (context,item);
        }

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }
}
