package com.example.lenovo.zyy.myadapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.collback.RecyclerItemClick;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by lenovo on 2017/5/8.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<EMConversation> list;
    private RecyclerItemClick recyclerItemClick;
    private Map<String, String> testStr = new ArrayMap<> ();

    //草稿接口  setter
    public void setTestStr(Map<String, String> testStr) {
        this.testStr = testStr;
        notifyDataSetChanged ();
    }

    //子项监听接口  setter
    public void setRecyclerItemClick(RecyclerItemClick recyclerItemClick) {
        this.recyclerItemClick = recyclerItemClick;
    }

    public MyAdapter(Context context, ArrayList<EMConversation> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, textview, content, time, unread;
        private ImageView img;
        private View view;

        public MyViewHolder(View itemView) {
            super (itemView);
            img = (ImageView) itemView.findViewById (R.id.item_chat_title_img);
            name = (TextView) itemView.findViewById (R.id.item_chat_title_name);
            textview = (TextView) itemView.findViewById (R.id.item_chat_title_string);
            content = (TextView) itemView.findViewById (R.id.item_chat_title_str);
            time = (TextView) itemView.findViewById (R.id.item_chat_aaa);
            unread = (TextView) itemView.findViewById (R.id.item_chat_bbb);
            view = itemView.findViewById (R.id.item_chat_rl);
        }
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MyAdapter.MyViewHolder (LayoutInflater.from (context).inflate (R.layout.item_chatlist, parent, false));
    }


    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
//        获取当前数据源
        EMConversation mData = list.get (position);
//        获取会话类型
        mData.getType ();
//        获取最后一条信息
        EMMessage lastMsg = mData.getLastMessage ();
//        获得消息类型
        EMMessage.Type msgType = lastMsg.getType ();
//        根据消息类型进行判断
        switch (msgType) {
            case TXT:
                holder.textview.setText ("文字：");
                EMTextMessageBody txtMsg = (EMTextMessageBody) lastMsg.getBody ();
//                消息内容
                holder.content.setText (txtMsg.getMessage ());
                break;
            case IMAGE:
                holder.textview.setText ("图片：");
                break;
            case VIDEO:
                break;
        }


//        获得会话名
        String userName = mData.getUserName ();
//        设置名字
        holder.name.setText (userName);


//      holder.time.setText(String.valueOf(list.get(position).getLastMessage().getMsgTime()));
//        获得消息时间
        long msgTime = lastMsg.getMsgTime ();
//        设置时间
        SimpleDateFormat sdf = new SimpleDateFormat ("MM_dd");
        Date d = new Date (msgTime);
        String time1 = sdf.format (d);
        holder.time.setText (String.valueOf (time1));


//        获得未读消息数
        int unRead = mData.getUnreadMsgCount ();
//        设置未读消息数
        if (unRead > 99) {
            holder.unread.setText ("99+");
        } else {
            holder.unread.setText ("" + unRead);
        }

        if (!TextUtils.isEmpty (testStr.get (userName))) {
            holder.textview.setText (testStr.get (userName) + "--");
        }

        holder.view.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                recyclerItemClick.onItemClick (position);
            }
        });
        holder.view.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View v) {
                return recyclerItemClick.onItemLongClick (position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }
}
