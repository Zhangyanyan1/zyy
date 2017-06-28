package com.example.lenovo.zyy.myadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.collback.RecyclerItemClick;
import com.example.lenovo.zyy.fragment.FaceSelectFragment;
import com.example.lenovo.zyy.model.FaceBean;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/6/6.
 */
public class FaceAdapter extends RecyclerView.Adapter<FaceAdapter.MyAdapter>{
    private Context context;
    private ArrayList<FaceBean> list;
    private RecyclerItemClick recyclerItemClick;

    public void setRecyclerItemClick(FaceSelectFragment recyclerItemClick) {
        this.recyclerItemClick = recyclerItemClick;
    }

    public FaceAdapter(Context context, ArrayList<FaceBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAdapter(LayoutInflater.from(context).inflate(R.layout.item_face_select, parent, false));
    }


    @Override
    public void onBindViewHolder(MyAdapter holder, final int position) {
        Glide.with(context)
                .load(list.get(position).getId())
                .override(100, 100)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyAdapter(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_face_img);
        }
    }
}
