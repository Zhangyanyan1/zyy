package com.example.lenovo.zyy.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.utils.StringUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import static com.hyphenate.chat.EMMessage.Type.TXT;

/**
 * Created by lenovo on 2017/5/23.
 */

public class TextHolder extends RecyclerView.ViewHolder{
    private TextView time,left_conten,right_conten;
    private ImageView lrftHeaderView,rightHeaderView,msgstatus;
    private View left_lay,right_lay;
    public TextHolder(View itemView) {
        super (itemView);
        time = (TextView) itemView.findViewById (R.id.item_message_left_show);
        left_lay =itemView.findViewById (R.id.item_message_left_lay);
        right_lay =itemView.findViewById (R.id.item_message_right_lay);

        lrftHeaderView = (ImageView) itemView.findViewById (R.id.item_message_left_header_img);
        rightHeaderView = (ImageView) itemView.findViewById (R.id.item_message_right_header_img);
        rightHeaderView = (ImageView) itemView.findViewById (R.id.item_message_right_status);

        left_conten = (TextView) itemView.findViewById (R.id.item_message_left_conten);
        right_conten = (TextView) itemView.findViewById (R.id.item_message_right_conten);

    }
    public void setView(Context context,EMMessage item) {
        if (item.getFrom ().equals (EMClient.getInstance ().getCurrentUser ())) {
            right_lay.setVisibility (View.VISIBLE);
            left_lay.setVisibility (View.GONE);
            if (item.getType () == TXT) {
                EMTextMessageBody txtMsg = (EMTextMessageBody) item.getBody ();
                SpannableString expressionString = StringUtil.getExpressionString (context, txtMsg.getMessage ());
                right_conten.setText (expressionString);
//                right_conten.setText (txtMsg.getMessage ());
            }
//            if (item.getType () == IMAGE) {
//               right_conten.setText ("[图片]");
//            }
        }else {
            left_lay.setVisibility (View.VISIBLE);
            right_lay.setVisibility (View.GONE);
            if (item.getType () == TXT) {
                EMTextMessageBody txtMsg = (EMTextMessageBody) item.getBody ();
                SpannableString expressionString = StringUtil.getExpressionString (context, txtMsg.getMessage ());
                left_conten.setText (expressionString);
//                left_conten.setText (txtMsg.getMessage ());

            }
//            if (item.getType () == IMAGE) {
//                left_conten.setText ("[图片]");
//            }
        }
    }
}
