package com.example.lenovo.zyy.aaa;

import android.app.Application;

import com.example.lenovo.zyy.utils.StringUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by lenovo on 2017/4/20.
 */
//开口类      可以当成全局的类
public class MyApplistcation extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

//        初始化第三方SDK在这初始化
        initEMsdk();
        StringUtil.list2Map ();
    }
    private void initEMsdk(){
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
//初始化
        EMClient.getInstance().init(this, options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

    }
}
