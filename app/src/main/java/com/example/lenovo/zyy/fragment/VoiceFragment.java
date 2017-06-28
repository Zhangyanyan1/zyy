package com.example.lenovo.zyy.fragment;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.MessageActivity;
import com.example.lenovo.zyy.utils.VoicePlay;

import java.io.File;

/**
 * Created by lenovo on 2017/6/12.
 */

public class VoiceFragment extends BaseFragment implements View.OnTouchListener {
    private MediaRecorder mediaRecorder;
    private ImageView img;
    private long starttime;
    private long finishtime;
    private int andioTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_voice, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        ivit (view);
    }

    private void ivit(View view) {
        img = (ImageView) view.findViewById (R.id.fragment_voice_img);
        img.setOnTouchListener (this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction ()==MotionEvent.ACTION_DOWN) {
            img.setImageResource (R.drawable.voice);
            starttime=System.currentTimeMillis ();

            VoicePlay.getInstance ().playVoice (getActivity ());

        }else if (event.getAction ()==MotionEvent.ACTION_UP){
            img.setImageResource (R.drawable.yuyin_on);
            finishtime=System.currentTimeMillis ();

            File file=VoicePlay.getInstance ().finish ();

            int time=(int)((finishtime-starttime)/1000);
            ((MessageActivity) getActivity())
                    .createAudio(file.getPath (), time);
        }
        return true;
    }

//    private void initRecode() {
//        mediaRecorder = new MediaRecorder ();
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//    }

//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                fileName = getFileName();
//                startRecode(fileName);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                endRecode();
//
//                getAudioTime();
//
//                ((MessageActivity) getActivity())
//                        .createAudio(fileName, audioTime);
//                break;
//        }
//        return true;
//    }
//
//    private void getAudioTime() {
//        MediaPlayer mp = new MediaPlayer();
//        try {
//            mp.setDataSource(fileName);
////            mp.prepare();
//            audioTime = mp.getDuration();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mp.reset();
//        mp.release();
//        mp = null;
//
//    }
//
//    private String getFileName() {
//        return "";
//    }
//
//    private void startRecode(String filePath) {
//        mediaRecorder.setOutputFile(filePath);
//        try {
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void endRecode() {
//        mediaRecorder.stop();
//        mediaRecorder.reset();
//        mediaRecorder.release();
//
//    }
}
