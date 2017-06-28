package com.example.lenovo.zyy.holder;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.VideoActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;

import java.io.File;
import java.util.Map;
import static com.hyphenate.chat.EMMessage.Type.VIDEO;

/**
 * Created by lenovo on 2017/5/23.
 */

public class VideoHolder extends RecyclerView.ViewHolder{
    private ImageView left_conten,right_conten;
    private View left_lay,right_lay;

    public VideoHolder(View itemView) {
        super (itemView);
        left_lay =itemView.findViewById (R.id.item_message_left_lay);
        right_lay =itemView.findViewById (R.id.item_message_right_lay);

        left_conten = (ImageView)  itemView.findViewById (R.id.item_message_left_conten);
        right_conten = (ImageView) itemView.findViewById (R.id.item_message_right_conten);

    }
    public void setView(final Context context, final EMMessage emMessage) {

        if (emMessage.getFrom ().equals (EMClient.getInstance ().getCurrentUser ())) {
            right_lay.setVisibility (View.VISIBLE);
            left_lay.setVisibility (View.GONE);
                if (emMessage.getType ()== VIDEO) {
//                    取出图片消息体
                   final EMVideoMessageBody imgBody= (EMVideoMessageBody) emMessage.getBody ();
                    Glide.with (context)
                            .load (imgBody.getLocalThumb ())
                            .override (200,300)
                            .into (right_conten);
                    right_conten.setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View view) {
                            String path=imgBody.getLocalUrl ();
                            Intent intent = new Intent (context, VideoActivity.class);
                            intent.putExtra ("videoPath",path);
                            context.startActivity (intent);
                        }
                    });
                }
        }else {
            left_lay.setVisibility (View.VISIBLE);
            right_lay.setVisibility (View.GONE);
            if (emMessage.getType ()== VIDEO) {
                final EMVideoMessageBody imgBody = (EMVideoMessageBody) emMessage.getBody ();
                Glide.with (context)
                        .load (imgBody.getThumbnailUrl ())
                        .into (left_conten);
                left_conten.setOnClickListener (new View.OnClickListener () {

                    @Override
                    public void onClick(View view) {
//                        拿到视频本地地址
                        String path = imgBody.getLocalUrl ();
//                        判断视频是否存在
                        if (new File (path).exists ()) {
//                            如果存在  则播放
                            Intent intent = new Intent (context, VideoActivity.class);
                            intent.putExtra ("videoPath", path);
                            context.startActivity (intent);
                        } else {
//                            下载的目标文件地址
                            final String locPath = Environment.getExternalStorageDirectory () + ".mp4";

//                            下载所需的秘钥
                            Map<String, String> map = new ArrayMap<> ();
                            if (TextUtils.isEmpty (imgBody.getSecret ())) {
                                map.put ("share-secret", imgBody.getSecret ());

//                                调用下载方法
                                EMClient.getInstance ()
                                        .chatManager ()
                                        .downloadFile (
                                                imgBody.getRemoteUrl ()
                                                , locPath
                                                , map
                                                , new EMCallBack () {
                                                    @Override
                                                    public void onSuccess() {
                                                        Log.e ("dowenload", "onSuccess");
//                                                        修改消息的本地uri
                                                        ((EMVideoMessageBody) emMessage.getBody ())
                                                                .setLocalUrl (locPath);
//                                                         修改消息
                                                        EMClient.getInstance ()
                                                                .chatManager ()
                                                                .updateMessage (emMessage);

                                                    }

                                                    @Override
                                                    public void onError(int i, String s) {

                                                    }

                                                    @Override
                                                    public void onProgress(int i, String s) {

                                                    }
                                                }
                                        );
                            }
                        }
                    }
                });
            }
        }
    }
}
