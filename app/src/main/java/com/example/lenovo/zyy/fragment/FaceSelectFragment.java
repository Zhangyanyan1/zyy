package com.example.lenovo.zyy.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.MessageActivity;
import com.example.lenovo.zyy.collback.RecyclerItemClick;
import com.example.lenovo.zyy.model.FaceBean;
import com.example.lenovo.zyy.myadapter.FaceAdapter;
import com.example.lenovo.zyy.utils.StringUtil;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/6/6.
 */

public class FaceSelectFragment extends BaseFragment implements RecyclerItemClick{
    private RecyclerView recyclerView;
    private ArrayList<FaceBean> list = StringUtil.getList();
    private FaceAdapter faceAdapter;

    // 表情fragment 列 数
    private static final int FACE_LIST_COLUMNS = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_face_select,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        initView(view);
    }
    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_face_list);

        // 获取表情数据源


        faceAdapter = new FaceAdapter(getActivity(), list);

        GridLayoutManager glm = new GridLayoutManager(getActivity(), FACE_LIST_COLUMNS);

        faceAdapter.setRecyclerItemClick (this);

        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(faceAdapter);
    }
    @Override
    public void onItemClick(int index) {
        FaceBean faceBean = list.get(index);

        ((MessageActivity) getActivity()).setFace(faceBean.getName());
    }

    @Override
    public boolean onItemLongClick(int index) {
        return false;
    }
}
