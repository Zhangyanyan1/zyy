package com.example.lenovo.zyy.message;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.lenovo.zyy.collback.IMessageList;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lenovo on 2017/5/10.
 */

public class MessageManager {
    private IMessageList messageList;
    private static MessageManager messageManager=new MessageManager ();

    public static MessageManager getInstance(){
        return messageManager;
    }

    public IMessageList getiMessageList() {
        return messageList;
    }

    public void setiMessageList(IMessageList messageList) {
        this.messageList = messageList;
    }

    public EMMessage createTxt(String content, String name, EMMessage.ChatType type) {
        EMMessage msg = EMMessage.createTxtSendMessage (content, name);
        sendMsg (msg);
        return msg;
    }

    public EMMessage createImaage(String path, String name, boolean sengIng, EMMessage.ChatType type) {
        EMMessage msg = EMMessage.createImageSendMessage (path, sengIng, name);
        sendMsg (msg);
        return  msg;
    }

    public EMMessage createAudio(String filePath, int andioTime, String name, EMMessage.ChatType type) {
        EMMessage msg = EMMessage.createVoiceSendMessage (filePath, andioTime, name);
        sendMsg (msg);
        return msg;
    }

    /**
     * 发送视频消息
     * @param data
     * @param name
     */
    public EMMessage createVideo(Context context,Intent data,String name,EMMessage.ChatType type) {
        String videoPath=getVideoPath (context,data);

        int videoTime=getVideoTime(videoPath);
        File videoImg = getVideoImg (videoPath);
        EMMessage msg =EMMessage.createVideoSendMessage (
                videoPath
                ,videoImg.getAbsolutePath ()
                ,videoTime
                ,name);
        sendMsg (msg);
        return msg;
    }
    private String getVideoPath(Context context,Intent data) {
        String videoPath="";
        ContentResolver cr = context.getContentResolver ();
        Cursor c = cr.query (data.getData ()
                , new String[]{MediaStore.Video.Media.DATA}
                , null
                , null
                , null);
        if (c != null) {
            if (c.moveToFirst ()) {
                int index= c.getColumnIndex (MediaStore.Video.Media.DATA);
                videoPath = c.getString (index);
            }
        }
        return videoPath;
    }

    private File getVideoImg(String videoPath) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever ();
        mmr.setDataSource (videoPath);
        Bitmap bitmap = mmr.getFrameAtTime (1000);
        return bitmap2file (bitmap);
    }

    private File bitmap2file(Bitmap bitmap) {
        String videoImgName = System.currentTimeMillis () + ".jpg";
        File file = new File (Environment.getExternalStorageDirectory ()
                , videoImgName);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream (file);
            bitmap.compress (Bitmap.CompressFormat.JPEG
                    , 50
                    , fileOutputStream);
            fileOutputStream.flush ();
            fileOutputStream.close ();
            bitmap=null;
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        return file;

    }

    private int getVideoTime(String videoPath) {
        int videoTime=0;
        MediaPlayer mediaPlayer = new MediaPlayer ();
        try {
            mediaPlayer.setDataSource (videoPath);
            videoTime=mediaPlayer.getDuration ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        mediaPlayer.reset ();
        mediaPlayer.release ();
        mediaPlayer = null;
        return videoTime;
    }
    private void sendMsg(final EMMessage msg) {

        msg.setMessageStatusCallback (new EMCallBack () {
            @Override
            public void onSuccess() {
                Log.e ("onSuccess","onSuccess");
            }

            @Override
            public void onError(int i, String s) {
                Log.e ("onError",s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        // 设置消息的会话类型
        msg.setChatType (EMMessage.ChatType.Chat);
        // 发送
        EMClient.getInstance ()
                .chatManager ()
                .sendMessage (msg);

        // 刷新会话列表
        MessageManager
                .getInstance ()
                .getiMessageList ()
                .refChatList ();
    }

}
