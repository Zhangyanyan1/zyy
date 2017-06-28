package com.example.lenovo.zyy.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenovo.zyy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/6/20.
 */

public class GroupInfoAdapter extends RecyclerView.Adapter<GroupInfoAdapter.MyAdapter> {
    private List<String> list=new ArrayList<> ();
    private Context context;

    public GroupInfoAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyAdapter(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_group_info_textView);
        }
    }
    @Override
    public GroupInfoAdapter.MyAdapter onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyAdapter (LayoutInflater.from (context).inflate (R.layout.item_group_info,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(GroupInfoAdapter.MyAdapter myAdapter, int i) {
           myAdapter.textView.setText (list.get (i));
    }

    @Override
    public int getItemCount() {
        return list.size ();
    }
}
