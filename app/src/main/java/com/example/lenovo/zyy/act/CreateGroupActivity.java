package com.example.lenovo.zyy.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.message.MessageManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by lenovo on 2017/6/19.
 */

public class CreateGroupActivity extends BaseActivity {
    private Button button;
    private EditText editText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_create_group);
        initView ();

    }

    private void initView() {
        button = (Button) findViewById (R.id.create_group_but);
        editText = (EditText) findViewById (R.id.create_group_edit);
        recyclerView = (RecyclerView) findViewById (R.id.create_group_list);
        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                try {
                    EMGroupManager.EMGroupOptions op=new EMGroupManager.EMGroupOptions ();
                    op.style=EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
                    EMGroup group = EMClient.getInstance ()
                            .groupManager ()
                            .createGroup (
                                    "群:  " + System.currentTimeMillis ()
                                    , ""
                                    , new String[]{}
                                    , ""
                                    , op);

                    if (group != null) {
                        Toast.makeText (CreateGroupActivity.this,"创建成功",Toast.LENGTH_SHORT).show ();
                        MessageManager.getInstance ().createTxt (
                                "大家好"
                                ,group.getGroupId()
                                ,EMMessage.ChatType.GroupChat
                        );
                        CreateGroupActivity.this.finish ();
                    }
                } catch (HyphenateException e) {
                    Toast.makeText (CreateGroupActivity.this,"创建失败",Toast.LENGTH_SHORT).show ();
                    e.printStackTrace ();
                }

            }
        });

    }
}
