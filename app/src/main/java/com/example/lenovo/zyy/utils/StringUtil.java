package com.example.lenovo.zyy.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.model.FaceBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/6/6.
 */
public class StringUtil {
    static HashMap<String, Integer> emojiMap = new HashMap ();

    public static void list2Map() {
        emojiMap.put ("[wx]", R.mipmap.ic_launcher);
    }

    public static ArrayList<FaceBean> getList() {
        ArrayList<FaceBean> list = new ArrayList<> ();
        Set<String> str = emojiMap.keySet ();
        Iterator<String> it = str.iterator ();
        while (it.hasNext ()) {
            String name = it.next ();
            list.add (new FaceBean (name, emojiMap.get (name)));
        }
        return list;
    }

    public static SpannableString getExpressionString(Context context, String str) {
        SpannableString spannableString = new SpannableString (str);

        String zhengze = "\\[[^\\]]+\\]";

        Pattern sinaPattern = Pattern.compile (zhengze, Pattern.CASE_INSENSITIVE);
        try {
            dealExpression (context, spannableString, sinaPattern, 0);
        } catch (Exception e) {
            Log.e ("dealExpression", e.getMessage ());
        }
        return spannableString;
    }

    private static void dealExpression(Context context,
                                       SpannableString spannableString, Pattern patten, int start)
            throws Exception {
        Matcher matcher = patten.matcher (spannableString);
        while (matcher.find ()) {
            String key = matcher.group ();
            if (matcher.start () < start) {
                continue;
            }
            int resId = emojiMap.get (key);

            if (TextUtils.isEmpty (resId + "")) {
                continue;
            }
            if (resId != 0) {
                ImageSpan imageSpan = new ImageSpan (context, resId);
                int end = matcher.start () + key.length ();
                spannableString.setSpan (imageSpan, matcher.start (), end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
              if (end<spannableString.length ()){
                  dealExpression (context,spannableString,patten,end);
              }
                break;
            }
        }
    }
}
