package com.example.lenovo.zyy.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by lenovo on 2017/6/13.
 */

public class PlayerUtil extends MediaPlayer{
    public static int StreamType= AudioManager.STREAM_MUSIC;
    private MediaPlayer mp;
    private static PlayerUtil playerUtil;

    public static synchronized PlayerUtil getInstance(){
        if (playerUtil==null){
            playerUtil=new PlayerUtil ();
        }
        return playerUtil;
    }
    public void Play(String path){
        if (mp==null){
            mp=new MediaPlayer ();
            mp.setAudioStreamType (StreamType);
            try {
                mp.setDataSource (path);
                mp.prepare ();
                mp.start ();
                mp.setOnCompletionListener (new OnCompletionListener () {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mp.stop ();
                        mp.reset ();
                        mp.release ();
                        mp=null;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }
}
