package com.example.lenovo.zyy.utils;

import android.content.Context;
import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

/**
 * Created by lenovo on 2017/6/13.
 */

public class VoicePlay {
    private static VoicePlay voicePlay;
    private MediaRecorder mediaRecorder;
    File file;
    public static synchronized VoicePlay getInstance(){
        if (voicePlay==null){
            voicePlay=new VoicePlay ();
        }
        return voicePlay;
    }
    public void playVoice(Context context){
        if (mediaRecorder==null) {
            mediaRecorder = new MediaRecorder ();
        }
        file=new File (context.getCacheDir ()+String.valueOf (System.currentTimeMillis ())+".amr");
        mediaRecorder = new MediaRecorder ();
        mediaRecorder.setAudioSource (MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat (MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder (MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile (file.getPath ());
        try {
           mediaRecorder.prepare ();
            mediaRecorder.start ();

        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
    public File finish(){
        mediaRecorder.stop ();
        mediaRecorder.release ();
        mediaRecorder=null;
        return file;
    }
}
