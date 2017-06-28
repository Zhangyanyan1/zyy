package com.example.lenovo.zyy.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.MessageActivity;
import com.example.lenovo.zyy.myadapter.SelectImageAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by lenovo on 2017/4/28.
 */

public class ImageSelectFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button sendBtn;
    private ArrayList<String> list = new ArrayList<> ();
    private SelectImageAdapter sia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_imageselect, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById (R.id.fragment_imagr_select_recy);
        sendBtn = (Button) view.findViewById (R.id.fragment_imagr_select_sendBtn);
        sendBtn.setOnClickListener (this);
        getImagepath ();

        sia = new SelectImageAdapter (getActivity (), list);
        LinearLayoutManager llm = new LinearLayoutManager (getActivity ());
        llm.setOrientation (LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager (llm);
        recyclerView.setAdapter (sia);
    }

    /**
     * 查询所有图片路径
     */
    private void getImagepath() {
//        清空图片数据源
        list.clear ();
//        查询图片数据
        Cursor datas = getActivity ().getContentResolver ().query (
//                查询
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                要查询的uri
                , null//要查询的这段
                , null//查询的条件
                , null//查询条件的值
                , null);//排序
//        判断查询结果
        if (datas != null) {
//        遍历游标
            while (datas.moveToNext ()) {
//                获取当前游标中 图片路径
                String path = datas.getString (datas.getColumnIndex (MediaStore.Images.Media.DATA));

                int w = datas.getInt (datas.getColumnIndex (MediaStore.Images.Media.WIDTH));
                int H = datas.getInt (datas.getColumnIndex (MediaStore.Images.Media.HEIGHT));
                list.add (path);
            }
            datas.close ();
        }
    }

    @Override
    public void onClick(View v) {

//        拿到容器Activity对象
        MessageActivity ma = (MessageActivity) getActivity ();
//        获取选中图片的数据
        HashSet<String> paths = sia.getPaths ();
//        遍历set集合
        Iterator<String> iterator = paths.iterator ();
        while (iterator.hasNext ()) {
//            拿到集合中每个图片的路径  发送出去
            String str = iterator.next ();
            ma.sendImg(str);
        }

    }
}
