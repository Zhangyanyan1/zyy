package com.example.lenovo.zyy.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zyy.R;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by lenovo on 2017/5/23.
 */

public class SelectImageAdapter extends RecyclerView.Adapter<SelectImageAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<String> list=new ArrayList<> ();
    private HashSet<String> paths=new HashSet<> ();

    public HashSet<String> getPaths() {
        return paths;
    }

    public void setPaths(HashSet<String> paths) {
        this.paths = paths;
    }

    public SelectImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private CheckBox cb;
        public MyViewHolder(View itemView) {
            super (itemView);
            iv = (ImageView) itemView.findViewById (R.id.item_selec_image_iv);
            cb = (CheckBox) itemView.findViewById (R.id.item_selec_image_cb);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectImageAdapter.MyViewHolder (LayoutInflater.from (context).inflate (R.layout.item_selecimage, parent, false));

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with (context)
                .load (list.get (position))
                .override (200,300)
                .into (holder.iv);
        holder.cb.setOnCheckedChangeListener (new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    paths.add (list.get (position));
                }else {
                    paths.remove (list.get (position));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size ();
    }
}
