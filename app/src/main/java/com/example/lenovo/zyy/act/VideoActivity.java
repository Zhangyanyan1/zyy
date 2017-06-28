package com.example.lenovo.zyy.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.lenovo.zyy.R;

/**
 * Created by lenovo on 2017/6/14.
 */

public class VideoActivity extends BaseActivity{
    private VideoView videoview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        videoview = (VideoView) findViewById (R.id.videoview);
        String videoPath = getIntent ().getStringExtra ("videoPath");
        videoview.setVideoPath (videoPath);
        videoview.start ();
    }
}
