package com.example.lenovo.zyy.act;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lenovo on 2017/4/20.
 */

public class BaseActivity extends AppCompatActivity{

    public void inrent2Login(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);

    }
    public void inrent2Main(){
//        EMClient.getInstance().groupManager().loadAllGroups();
//        EMClient.getInstance().chatManager().loadAllConversations();

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
