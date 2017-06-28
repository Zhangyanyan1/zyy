package com.example.lenovo.zyy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.CreateGroupActivity;

/**
 * Created by lenovo on 2017/4/27.
 */

public class TestFragment extends BaseFragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        ivit(view);
    }

    private void ivit(View view) {
        view.findViewById (R.id.fragment_test_but1).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity (new Intent (getActivity (), CreateGroupActivity.class ));
            }
        });
    }
}
