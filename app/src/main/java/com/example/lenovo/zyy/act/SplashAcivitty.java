package com.example.lenovo.zyy.act;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.lenovo.zyy.R;
import com.hyphenate.chat.EMClient;

public class SplashAcivitty extends BaseActivity {
    private TextView splash;
    //handler线程兼通信
     Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            inrent2Next();
        }
    };

    /**
     * 跳转到下个页面
     */
    private void inrent2Next() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            inrent2Main();
        } else {
            inrent2Login();
        }
        // 销毁当前界面。
        SplashAcivitty.this.finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         splash = (TextView) findViewById(R.id.splash_tv);
        final Mycount my = new Mycount(4000, 1000);
        // 开启计时器
        my.start();
//        handler.sendEmptyMessageDelayed(1, 3000);
        splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeMessages(1);
                inrent2Next();
                my.cancel();
            }
        });
    }
//返回键失效
    @Override
    public void onBackPressed() {
    }

    private class Mycount extends CountDownTimer {
        /**
         * @param millisInFuture
         *            总共持续多长时间
         *
         * @param countDownInterval
         *            时间间隔
         *
         */ // 4 1
        public Mycount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 每隔countDownInterval的一段时间就会掉用的方法。
         *
         * @param millisUntilFinished
         *            剩余的时间
         */
        @Override
        public void onTick(long millisUntilFinished) {
            splash.setText("跳过" + millisUntilFinished / 1000 + "s");
        }

        /**
         * 当计时器计时结束的时候调用的方法。
         *
         */
        @Override
        public void onFinish() {
            // Intent i=new Intent(WelcomeActivity.this,MainActivity.class);
            // startActivity(i);

            // 开启主界面
            inrent2Next();
        }
    }
}
