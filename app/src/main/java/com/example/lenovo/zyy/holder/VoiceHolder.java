package com.example.lenovo.zyy.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.utils.PlayerUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;

/**
 * Created by lenovo on 2017/5/23.
 */

public class VoiceHolder extends RecyclerView.ViewHolder{
    private ImageView left_conten,right_conten;
    private View left_lay,right_lay;

    public VoiceHolder(View itemView) {
        super (itemView);
        left_lay =itemView.findViewById (R.id.item_message_left_lay);
        right_lay =itemView.findViewById (R.id.item_message_right_lay);

        left_conten = (ImageView) itemView.findViewById (R.id.item_message_left_conten);
        right_conten = (ImageView) itemView.findViewById (R.id.item_message_right_conten);

    }
    public void setView(EMMessage emMessage) {
        final EMVoiceMessageBody voiceBody= (EMVoiceMessageBody) emMessage.getBody ();
          float time=voiceBody.getLength ();
        if (emMessage.getFrom ().equals (EMClient.getInstance ().getCurrentUser ())) {
            right_lay.setVisibility (View.VISIBLE);
            left_lay.setVisibility (View.GONE);

               if (voiceBody.downloadStatus ().equals (EMFileMessageBody.EMDownloadStatus.SUCCESSED)){
                   right_conten.setOnClickListener (new View.OnClickListener () {
                       @Override
                       public void onClick(View view) {
                           PlayerUtil.getInstance ().Play (voiceBody.getLocalUrl ());
                       }
                   });
               }
        }else {
            left_lay.setVisibility (View.VISIBLE);
            right_lay.setVisibility (View.GONE);
//            判断消息类型
            if (voiceBody.downloadStatus ().equals (EMFileMessageBody.EMDownloadStatus.SUCCESSED)){
                left_conten.setOnClickListener (new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                        PlayerUtil.getInstance ().Play (voiceBody.getLocalUrl ());
                    }
                });
            }
        }
    }
}
