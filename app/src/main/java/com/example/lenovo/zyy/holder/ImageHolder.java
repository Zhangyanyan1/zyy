package com.example.lenovo.zyy.holder;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.utils.ImageUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;

import static com.hyphenate.chat.EMMessage.Type.IMAGE;

/**
 * Created by lenovo on 2017/5/23.
 */

public class ImageHolder extends RecyclerView.ViewHolder{
    private ImageView left_conten,right_conten;
    private View left_lay,right_lay;

    public ImageHolder(View itemView) {
        super (itemView);
        left_lay =itemView.findViewById (R.id.item_message_left_lay);
        right_lay =itemView.findViewById (R.id.item_message_right_lay);

        left_conten = (ImageView) itemView.findViewById (R.id.item_message_left_conten);
        right_conten = (ImageView) itemView.findViewById (R.id.item_message_right_conten);

    }
    public void setView(Context context,EMMessage item) {
        if (item.getFrom ().equals (EMClient.getInstance ().getCurrentUser ())) {
            right_lay.setVisibility (View.VISIBLE);
            left_lay.setVisibility (View.GONE);
            if (item.getType () == IMAGE) {
                EMImageMessageBody imgBody= (EMImageMessageBody) item.getBody ();
                int wight= ImageUtil.minWidth,height=ImageUtil.minHeight;

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.BASE) {
                    Size size = ImageUtil.getWidthAndHeight (imgBody.getWidth (), imgBody.getHeight ());
                    wight = size.getWidth ();
                    height = size.getHeight ();
                }else {
                    //TODO 小于API21时 使用其他方法
                }
                Glide.with (context)
                        .load (imgBody.getLocalUrl ())
                        .override (wight,height)
                        .into (right_conten);
            }
        }else {
            left_lay.setVisibility (View.VISIBLE);
            right_lay.setVisibility (View.GONE);
//            判断消息类型
            if (item.getType () == IMAGE) {
//                取出图片消息体
                EMImageMessageBody imgBody= (EMImageMessageBody) item.getBody ();
                int wight= ImageUtil.minWidth,height=ImageUtil.minHeight;

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.BASE) {
                    Size size = ImageUtil.getWidthAndHeight (imgBody.getWidth (), imgBody.getHeight ());
                    wight = size.getWidth ();
                    height = size.getHeight ();
                }else {
                    //TODO 小于API21时 使用其他方法
                }
                Glide.with (context)
                        .load (imgBody.getLocalUrl ())
                        .override (wight,height)
                        .into (left_conten);
            }
        }
    }
}
