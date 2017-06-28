package com.example.lenovo.zyy.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.myadapter.GroupInfoAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.List;

/**
 * Created by lenovo on 2017/6/20.
 */

public class GroupInfoActivity extends BaseActivity {
    private Button button;
    private RecyclerView recyclerView;
    private EditText editText;
    private List<String> list;
    private String groupName;
    private GroupInfoAdapter groupInfoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_group_info);
        groupName = getIntent ().getStringExtra ("groupName");
        itiv ();
    }

    private void itiv() {
        button = (Button) findViewById (R.id.group_info_but);
        editText = (EditText) findViewById (R.id.group_info_edit);
        recyclerView = (RecyclerView) findViewById (R.id.group_info_recyclerView);

        list = EMClient.getInstance ().groupManager ().getGroup (groupName).getMembers ();
        groupInfoAdapter = new GroupInfoAdapter (list, this);
        LinearLayoutManager llm = new LinearLayoutManager (this);
        recyclerView.setLayoutManager (llm);
        recyclerView.setAdapter (groupInfoAdapter);

        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                String name = editText.getText ().toString ();
                EMClient.getInstance ().groupManager ().asyncAddUsersToGroup (groupName, new String[]{name}, new EMCallBack () {

                    @Override
                    public void onSuccess() {

                        runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                Toast.makeText (GroupInfoActivity.this, "添加完成", Toast.LENGTH_SHORT).show ();
                                list = EMClient.getInstance ().groupManager ().getGroup (groupName).getMembers ();
                                groupInfoAdapter.notifyDataSetChanged ();
                            }
                });
                             }

                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });
    }
}
