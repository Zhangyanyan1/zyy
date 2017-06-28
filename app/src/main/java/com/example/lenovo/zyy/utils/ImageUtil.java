package com.example.lenovo.zyy.utils;

import android.util.Size;

/**
 * Created by lenovo on 2017/5/23.
 */

public class ImageUtil {
    public static int minWidth=150,minHeight=150,maxWidth=400,maxHeight=400;
    public static Size getWidthAndHeight(int width,int height){
        int cWidth,cHeight;
        int t=0;
       if (width<height) {
           t=width;
           width=height;
           height=t;
       }


           if (width>maxWidth){
               double shang=width/(double)maxWidth;
               cWidth=maxWidth;
               cHeight=(int)(height/shang);
               cHeight=cHeight>maxHeight?cHeight:maxHeight;
           }else {
              if (width<minWidth){
                  double shang=minWidth/(double)width;
                  cWidth=minWidth;
                  cHeight= (int) (height*shang);
                  cHeight=cHeight>minHeight?cHeight:minHeight;
              }else {
                  cWidth=minWidth;
                  cHeight=height>minHeight?height:minHeight;
              }
           }
       if (t>0){
           t=cWidth;
           cWidth=cHeight;
           cHeight=t;
       }
        return new Size(cWidth,cHeight);
    }
}
